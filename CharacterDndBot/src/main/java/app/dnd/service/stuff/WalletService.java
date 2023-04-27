package app.dnd.service.stuff;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dnd.model.stuffs.Wallet;
import app.dnd.repository.WalletRepository;

@Transactional
@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;

	public Wallet findByUserIdAndOwnerName(Long id, String ownerName) {
		Optional<Wallet> userOptional = walletRepository.findByUserIdAndOwnerName(id,ownerName);
		return userOptional.orElseGet(() -> Wallet.build(id, ownerName));
	}

	public void save(Wallet wallet) {
		walletRepository.save(wallet);
	}

	public void deleteByIdAndOwnerName(Long id, String ownerName) {
		walletRepository.deleteByUserIdAndOwnerName(id, ownerName);
	}

}