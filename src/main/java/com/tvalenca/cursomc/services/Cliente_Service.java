package com.tvalenca.cursomc.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tvalenca.cursomc.domain.Cidade;
import com.tvalenca.cursomc.domain.Cliente;
import com.tvalenca.cursomc.domain.Endereco;
import com.tvalenca.cursomc.domain.enums.Perfil;
import com.tvalenca.cursomc.domain.enums.Tipo_Cliente;
import com.tvalenca.cursomc.dto.ClienteNewDTO;
import com.tvalenca.cursomc.dto.Cliente_DTO;
import com.tvalenca.cursomc.repositories.Cliente_Repository;
import com.tvalenca.cursomc.repositories.Endereco_Repository;
import com.tvalenca.cursomc.security.UserSS;
import com.tvalenca.cursomc.services.exceptions.AuthorizationException;
import com.tvalenca.cursomc.services.exceptions.DataIntegrityException;
import com.tvalenca.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class Cliente_Service {
    
    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private Cliente_Repository repo;

    @Autowired
    private Endereco_Repository enderecoRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private Image_Service image_Service;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    public Cliente buscar(Integer id){

        UserSS user = User_Service.authenticated();
        if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())){
            throw new AuthorizationException("Acesso Negado");
        }
        
        Optional<Cliente> obj = repo.findById(id); 
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: "
                                     + Cliente.class.getName()));
    }

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

    public Cliente update(Cliente obj){
        Cliente newObj = buscar(obj.getId());
        updateData(newObj,obj);
        return repo.save(newObj);
    }

    public void delete(Integer id){
        buscar(id);
        try{
            repo.deleteById(id);
        }
        catch(DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possivel excluir porque há entidades relacionadas");
        }

    }

    public List<Cliente> findAll(){
        return repo.findAll();
    }

    public Cliente findByEmail(String email){
        UserSS user = User_Service.authenticated();
        if(user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())){
            throw new AuthorizationException("Acesso Negado");
        }

        Cliente obj = repo.findByEmail(email);
        if(obj == null){
            throw new ObjectNotFoundException( "Objeto não encontrado! Id: " + user.getId() 
                                                + ", Tipo: " + Cliente.class.getName());
        }
        return obj;
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDto(Cliente_DTO objDto){
        return new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(),null,null, null);
    }

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCpnj(), Tipo_Cliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
    
    private void updateData(Cliente newObj, Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile){
        UserSS user = User_Service.authenticated();
        if(user == null){
            throw new AuthorizationException("Acesso Negado");
        }

        BufferedImage jpgImage = image_Service.getJpgImageFromFile(multipartFile);
        String fileName = prefix + user.getId() + ".jpg";

        return s3Service.uploadFile(image_Service.getInputStream(jpgImage, ".jpg"), fileName, "image");
    }
}