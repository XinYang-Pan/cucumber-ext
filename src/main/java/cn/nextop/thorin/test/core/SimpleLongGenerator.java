package cn.nextop.thorin.test.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.nextop.thorin.common.management.monitor.Monitor;
import cn.nextop.thorin.common.management.monitor.MonitorFactory;
import cn.nextop.thorin.common.util.Processes;
import cn.nextop.thorin.common.util.concurrent.PaddedAtomicLong;
import cn.nextop.thorin.common.util.generator.LongGenerator;

/**
 * 
 */
public class SimpleLongGenerator implements LongGenerator {
	//
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleLongGenerator.class);
	
	//
	protected static final int BITS3 = 40, BITS2 = 14, BITS1 = 2, BITS0 = 7;
	protected static final int BITS01 = BITS0 + BITS1, BITS012 = BITS01 + BITS2;
	protected static final long MASK1 = (1L << BITS1) - 1L, MASK0 = (1L << BITS0) - 1L;
	protected static final long MASK3 = (1L << BITS3) - 1L, MASK2 = (1L << BITS2) - 1L;
	protected static final long TIMESTAMP_OFFSET = 1451574000000L; // DateTimes.parse("yyyy-MM-dd HH:mm:ss.SSS", "2016-01-01 00:00:00.000");
	
	//
	protected final short aid;
	protected final String name;
	protected final Monitor monitor;
	protected final byte cid = (byte)0;
	protected final PaddedAtomicLong sequence = new PaddedAtomicLong(0);
	protected final ThreadLocal<PaddedAtomicLong> guards = new ThreadLocal<>();

	/**
	 * 
	 */
	protected SimpleLongGenerator(String name) {
		this.aid = getAid(); this.name = name; 
		this.monitor = MonitorFactory.getMonitor(name);
	}
	
	public SimpleLongGenerator() {
		this("SimpleLongGenerator");
	}
	
	/**
	 * 
	 */
	@Override
	public final long next() {
		return next(this.cid, 0L);
	}
	
	public final long next(long timestamp) {
		return next(this.cid, timestamp);
	}
	
	/**
	 * 
	 */
	public static final short getAppId(final long id) {
		return (short)(id & MASK0);
	}
	
	public static final byte getClusterId(final long id) {
		return (byte)((id >> BITS0) & MASK1);
	}
	
	public static final long getSequence(final long id) {
		return (long)(id >> (BITS01)) & MASK2;
	}
	
	public static final long getTimestamp(final long id) {
		return (id >> (BITS012)) + TIMESTAMP_OFFSET;
	}
	
	/**
	 * 
	 */
	public static final String encode36(final long value) {
		return Long.toString(value, 36);
	}
	
	public static final long decode36(final String value) {
		return Long.parseLong(value, 36);
	}
	
	public static final Long decode36(final String id, Long v) {
		try { return decode36(id); } catch(Throwable t) { return v; }
	}
	
	/**
	 * 
	 */
	private static final short getAid() {
		short r = (short)(Processes.getAid() & MASK0); if(r != 0) return r;
		LOGGER.warn("system property \"APP_ID\" has NOT been properly set");
		short processId = (short)(Processes.getPid() & MASK0); return processId;
	}
	
	/**
	 * 
	 */
	public final long next(byte cid, long timestamp) {
		if(timestamp == 0L) timestamp = System.currentTimeMillis();
			//
		final long v1 = cid & MASK1;
		final long v2 = this.sequence.getAndIncrement() & MASK2;
		final long v3 = (timestamp - TIMESTAMP_OFFSET) & MASK3;
		final long next = (v3 << BITS012) | (v2 << BITS01) | (v1 << BITS0) | this.aid;

		return next; 
	}
}
