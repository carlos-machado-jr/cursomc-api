package com.carlos.cursomc.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.carlos.cursomc.domain.Cidade;
import com.carlos.cursomc.domain.Cliente;
import com.carlos.cursomc.domain.Endereco;
import com.carlos.cursomc.domain.enums.Perfil;
import com.carlos.cursomc.domain.enums.TipoCliente;
import com.carlos.cursomc.dto.ClienteDTO;
import com.carlos.cursomc.dto.ClienteNewDTO;
import com.carlos.cursomc.repositories.ClienteRepository;
import com.carlos.cursomc.repositories.EnderecoRepository;
import com.carlos.cursomc.security.UserSS;
import com.carlos.cursomc.services.exceptions.AuthorizationException;
import com.carlos.cursomc.services.exceptions.DataIntegrityException;
import com.carlos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repo;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private S3Service s3Service;

	

//	@Value("${img.prefix.client.profile}")
//	private String prefix;
//
//	@Value("${img.profile.size}")
//	private Integer size;

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Cliente> c = repo.findById(id);
		return c.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto nao encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir porque há pedidos relacionadas");
		}

	}

//	PAGINAÇÃO
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

//	VALIDAÇÃO

	public Cliente fromDTO(ClienteDTO obj) {
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null, null);
	}

	public Cliente fromDTO(ClienteNewDTO obj) {
		Cliente cli = new Cliente(null, obj.getNome(), obj.getEmail(), obj.getCpfOuCnpj(),
				TipoCliente.toEnum(obj.getTipo()), pe.encode(obj.getSenha()));
		Cidade cid = new Cidade(obj.getCidadeId(), null, null);
		Endereco end = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(), cid,
				obj.getBairro(), obj.getCep(), cli);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(obj.getTelefone1());
		if (obj.getTelefone2() != null) {
			cli.getTelefones().add(obj.getTelefone2());
		}
		if (obj.getTelefone3() != null) {
			cli.getTelefones().add(obj.getTelefone3());
		}
		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());

	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {
//		UserSS user = UserService.authenticated();
//		if (user == null) {
//			throw new AuthorizationException("Acesso negado");
//		}
//
//		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
//		jpgImage = imageService.cropSquare(jpgImage);
//		jpgImage = imageService.resize(jpgImage, size);
//
//		String fileName = prefix + user.getId() + ".jpg";
//		imageService.getInputStream(jpgImage, "jpg"), fileName, "image"
		return s3Service.uploadFile(multipartFile);
	}
}
