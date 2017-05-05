package cn.nextop.thorin.test.service.holder;

import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import cn.nextop.thorin.test.service.common.AbstractService;

@Service
@Lazy
public class HolderService extends AbstractService {
	
	public void clearData(byte coId) {
		log.debug("Clear Holding data for company {}", coId);
		allocationPoService.deleteAll(coId);
		accountsPoService.deleteAll(coId);
		booksPoService.deleteAll(coId);
		bookPoService.deleteAll(coId);
	}

	public void clearData(Set<Byte> coIds) {
		log.debug("Clear Holding data for companies {}", coIds);
		for (Byte coId : coIds) {
			this.clearData(coId);
		}
	}

}
