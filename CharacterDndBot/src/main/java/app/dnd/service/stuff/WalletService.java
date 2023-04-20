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

	public Wallet findByIdAndOwnerName(Long id, String ownerName) {
		Optional<Wallet> userOptional = walletRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return Wallet.build(id, ownerName);
		}
	}

	public void save(Wallet wallet) {
		walletRepository.save(wallet);
	}

	public void deleteByIdAndOwnerName(Long id, String ownerName) {
		walletRepository.deleteByIdAndOwnerName(id, ownerName);
	}

}