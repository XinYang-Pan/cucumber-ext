package cn.nextop.thorin.test.emulator;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import cn.nextop.thorin.common.glossary.Lifecyclet;
import cn.nextop.thorin.common.util.Strings;
import cn.nextop.thorin.test.emulator.bo.GwQuote;
import quickfix.ConfigError;
import quickfix.DataDictionary;
import quickfix.DefaultMessageFactory;
import quickfix.FieldConvertError;
import quickfix.FieldNotFound;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.LogFactory;
import quickfix.Message;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;
import quickfix.UnsupportedMessageType;
import quickfix.field.MsgType;
import quickfix.fix44.MarketDataRequest;

public abstract class AbstractEmulator<T extends GwQuote> extends Lifecyclet implements EmulatorApplication {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	// set from constructor
	protected final CountDownLatch latch = new CountDownLatch(1);
	protected final String itName;
	protected final InputStream settingInputStream;
	// set from start
	protected SessionSettings settings;
	protected DataDictionary dataDictionary;
	protected SocketAcceptor acceptor;
	// set from children
	protected String messageTemplate;
	// set from MARKET_DATA_REQUEST msg
	protected MarketDataRequest request;
	protected SessionID sessionId;

	public AbstractEmulator(String itName, InputStream inputStream) {
		this.itName = itName;
		this.settingInputStream = inputStream;
	}

	/**
	 * 
	 */
	@Override
	protected void doStart() throws Exception {
		settings = new SessionSettings(settingInputStream);
		dataDictionary = new DataDictionary(settings.getString("DataDictionary"));
		// 
		MessageStoreFactory storeFactory = new FileStoreFactory(settings);
		LogFactory logFactory = new FileLogFactory(settings);
		MessageFactory messageFactory = new DefaultMessageFactory();
		acceptor = new SocketAcceptor(this, storeFactory, settings, logFactory, messageFactory);
		acceptor.start();
	}

	/**
	 * 
	 */
	@Override
	protected long doStop(long timeout, TimeUnit unit) throws Exception {
		acceptor.stop();
		return timeout;
	}

	@Override
	public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		try {
			final String type = message.getHeader().getString(MsgType.FIELD);
			if (Strings.isEquals(type, MsgType.MARKET_DATA_REQUEST)) {
				this.request = (MarketDataRequest) message;
				this.sessionId = sessionId;
				latch.countDown();
			}
		} catch (Exception e) {
			log.error("from App Error", e);
			throw new RuntimeException(e);
		}
	}

	public void waitUtilReady() throws InterruptedException {
		latch.await();
		Assert.notNull(this.request);
		Assert.notNull(this.sessionId);
		Assert.notNull(this.dataDictionary);
		Assert.notNull(this.messageTemplate);
	}

	@SuppressWarnings("unchecked")
	public void sendGwQuote(GwQuote gwQuote) throws Exception {
		// 
		Message message = buildQuoteMessage((T) gwQuote);
		// send 
		log.debug("fix-msg: {}", message);
		Session.sendToTarget(message, sessionId);
	}

	protected abstract Message buildQuoteMessage(T gwQuote) throws Exception;

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------

	public SessionSettings getSettings() {
		return settings;
	}

	public int getPort() {
		try {
			return (int) settings.getLong("SocketAcceptPort");
		} catch (ConfigError | FieldConvertError e) {
			throw new RuntimeException(e);
		}
	}

}
