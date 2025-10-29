package com.generation.carona.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.generation.carona.model.Usuario;
import com.generation.carona.repository.UsuarioRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (username == null || username.trim().isEmpty()) {
			throw new UsernameNotFoundException("Usuário (e-mail) não pode ser vazio");
		}
		
		Optional<Usuario> usuario = usuarioRepository.findByEmail(username);

		if (usuario.isPresent()) {
			return new UserDetailsImpl(usuario.get());
		}else {
			throw new UsernameNotFoundException("Usuário não encontrado: " + username);
		}
			
	}
}  
