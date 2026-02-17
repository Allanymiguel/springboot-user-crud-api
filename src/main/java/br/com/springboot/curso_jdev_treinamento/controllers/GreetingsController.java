package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */
	@RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String greetingText(@PathVariable String name) {
		return "Hello " + name + "!";
	}

	@RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String retornaOlaMundo(@PathVariable String nome) {

		Usuario user = new Usuario();
		user.setNome(nome);
		usuarioRepository.save(user); // grava no banco de dados

		return "Olá mundo " + nome;

	}

	@GetMapping(value = "listatodos")
	@ResponseBody
	public ResponseEntity<List<Usuario>> listarUsuarios() {

		List<Usuario> usuarios = usuarioRepository.findAll(); // executa consulta no bando de dados

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); // retorna a lista em json

	}

	@PostMapping(value = "salvar")
	@ResponseBody
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {

		Usuario user = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}

	@PutMapping(value = "atualizar")
	@ResponseBody
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {

		if (usuario.getId() == null) {
			return new ResponseEntity<String>("Campo 'id' é obrigatório e deve ser maior que zero",
					HttpStatus.BAD_REQUEST);
		}

		Usuario user = usuarioRepository.saveAndFlush(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}

	@DeleteMapping(value = "deletar")
	@ResponseBody
	public ResponseEntity<String> salvar(@RequestParam Long idUser) {

		usuarioRepository.deleteById(idUser);

		return new ResponseEntity<String>("Usuario deletado com sucesso", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "buscaruserid")
	@ResponseBody
	public ResponseEntity<Usuario> buscarUserId(@RequestParam(name = "idUser") Long idUser) {

		Usuario user = usuarioRepository.findById(idUser).get();

		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}

	@GetMapping(value = "buscarpornome")
	@ResponseBody
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) {

		List<Usuario> usuarios = usuarioRepository.buscarPorNome(name.trim().toLowerCase());

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}

}
