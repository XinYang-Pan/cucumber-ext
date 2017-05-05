package cn.nextop.thorin.test.vo.id.obj.derivative;

import cn.nextop.thorin.core.dealing.domain.DealingGenerator;
import cn.nextop.thorin.core.holding.domain.HoldingGenerator;
import cn.nextop.thorin.test.vo.id.has.derivative.HasPositionId;
import cn.nextop.thorin.test.vo.id.obj.single.SymbolId;

public class PositionId extends SymbolId implements HasPositionId {
	private final int bookId;

	public PositionId(int bookId, short symbolId) {
		super(symbolId);
		this.bookId = bookId;
	}

	// -----------------------------
	// ----- Static
	// -----------------------------

	public static PositionId toPositionId(int bookId, short symbolId) {
		return new PositionId(bookId, symbolId);
	}

	public static PositionId toPositionId(long positionId) {
		int bookId = DealingGenerator.getBookIdByPositionId(positionId);
		short symbolId = DealingGenerator.getSymbolIdByPositionId(positionId);
		return toPositionId(bookId, symbolId);
	}

	// -----------------------------
	// ----- methods
	// -----------------------------

	@Override
	public long positionId() {
		return DealingGenerator.toPositionId(bookId, symbolId());
	}

	@Override
	public byte coId() {
		return HoldingGenerator.getCompanyIdByBookId(bookId);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PositionId [bookId=");
		builder.append(bookId);
		builder.append(", symbolId()=");
		builder.append(symbolId());
		builder.append("]");
		return builder.toString();
	}

	public int getBookId() {
		return bookId;
	}

}
