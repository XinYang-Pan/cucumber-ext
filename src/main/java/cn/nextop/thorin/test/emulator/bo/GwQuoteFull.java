package cn.nextop.thorin.test.emulator.bo;

import java.util.List;

public class GwQuoteFull extends GwQuote {
	private String symbolName;
	private List<GwBand> gwBands;
	
	public GwQuoteFull() {
	}
	
	public GwQuoteFull(String itName, String symbolName, List<GwBand> gwBands) {
		super();
		this.itName = itName;
		this.symbolName = symbolName;
		this.gwBands = gwBands;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GwQuoteFull [symbolName=");
		builder.append(symbolName);
		builder.append(", gwBands=");
		builder.append(gwBands);
		builder.append("]");
		return builder.toString();
	}

	public String getSymbolName() {
		return symbolName;
	}

	public void setSymbolName(String symbolName) {
		this.symbolName = symbolName;
	}

	public List<GwBand> getGwBands() {
		return gwBands;
	}

	public void setGwBands(List<GwBand> gwBands) {
		this.gwBands = gwBands;
	}
	
}
