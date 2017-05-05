package cn.nextop.thorin.test.emulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;

public interface EmulatorApplication extends quickfix.Application {
	static final Logger LOGGER = LoggerFactory.getLogger(EmulatorApplication.class);

	@Override
	default void onCreate(SessionID sessionId) {
		LOGGER.debug("Create - {}", sessionId);
	}

	@Override
	default void onLogon(SessionID sessionId) {
		LOGGER.debug("Logon  - {}", sessionId);
	}

	@Override
	default void onLogout(SessionID sessionId) {
		LOGGER.debug("Logout - {}", sessionId);
	}

	@Override
	default void toAdmin(Message message, SessionID sessionId) {
	}

	@Override
	default void toApp(Message message, SessionID sessionId) throws DoNotSend {
	}

	@Override
	default void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
	}

	public boolean start();

}
