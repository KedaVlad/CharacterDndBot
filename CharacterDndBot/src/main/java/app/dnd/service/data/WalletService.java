package app.dnd.service.data;

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

	public Wallet getById(Long id) {
		Optional<Wallet> userOptional = walletRepository.findById(id);
		if (userOptional.isPresent()) {
			return userOptional.get();
		} else {
			return Wallet.build(id);
		}
	}

	public void save(Wallet wallet) {
		walletRepository.save(wallet);
	}

}