package cn.nextop.thorin.test.emulator.impl.gkgoh;

import java.util.List;

import cn.nextop.thorin.test.emulator.AbstractEmulator;
import cn.nextop.thorin.test.emulator.bo.GwGroup;
import cn.nextop.thorin.test.emulator.bo.GwQuoteIncr;
import quickfix.Group;
import quickfix.Message;
import quickfix.field.MDReqID;
import quickfix.field.NoMDEntries;
import quickfix.fix44.MarketDataIncrementalRefresh;

public class GkgohEmulator extends AbstractEmulator<GwQuoteIncr> {

	public GkgohEmulator() {
		super("GKGOH", GkgohEmulator.class.getResourceAsStream("gkgoh-server-session.cfg"));
	}

	@Override
	protected void doStart() throws Exception {
		super.doStart();
		this.messageTemplate = "8=FIX.4.49=17835=X49=CNX34=452=20160701-02:31:13.24256=gkgoh1003fixstr262=10697z0uv039268=2279=0269=1278=311655=USD/JPY270=102.922271=2000000346=3279=2269=1278=320255=USD/JPY10=018";

	}

	@Override
	protected Message buildQuoteMessage(GwQuoteIncr gwQuoteIncr) throws Exception {
		MarketDataIncrementalRefresh message = new MarketDataIncrementalRefresh();
		message.fromString(messageTemplate, dataDictionary, false);
		message.setString(MDReqID.FIELD, request.getString(MDReqID.FIELD));
		message.setInt(NoMDEntries.FIELD, gwQuoteIncr.getGroups().size());
		// 
		List<Group> groups = message.getGroups(NoMDEntries.FIELD);
		for (int i = 0; i < groups.size(); i++) {
			message.removeGroup(groups.get(i));
		}
		for (GwGroup Gwgroup : gwQuoteIncr.getGroups()) {
			message.addGroup(Gwgroup.toGroup());
		}
		// 
		return message;
	}

}