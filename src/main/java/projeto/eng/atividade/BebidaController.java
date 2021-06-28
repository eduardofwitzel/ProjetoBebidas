package projeto.eng.atividade;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BebidaController {
	
	@Autowired
	BebidaRepository bebidarepository;
	
	@RequestMapping(method = RequestMethod.GET,
			path = "/bebida")
	public ResponseEntity<List<Bebida>> listarBebidas() {
		List<Bebida> bebidas = bebidarepository.findAll();
		return ResponseEntity.ok(bebidas);

	}
	
	@RequestMapping(method = RequestMethod.POST,
			path = "/bebida/add")
	public String adicionarBebida(@RequestBody Bebida bebida) { 
		bebidarepository.save(bebida);
		return "bebida adicionada com sucesso";
	}
	
	
	@GetMapping(path = {"/bebida/{id}"})
	public ResponseEntity<Bebida> pesquisarPorID(@PathVariable long id){
	   return bebidarepository.findById(id)
	           .map(record -> ResponseEntity.ok().body(record))
	           .orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping(value="/bebida/alterar/{id}")
	public ResponseEntity<Bebida> alterarBebida(@PathVariable long id,
	                                      @RequestBody Bebida bebida) {
	   return bebidarepository.findById(id)
	           .map(record -> {
	               record.setMarca(bebida.getMarca());
	               record.setValor(bebida.getValor());
	               record.setTipo(bebida.getTipo());
	               record.setVolume(bebida.getVolume());
	               Bebida updated = bebidarepository.save(record);
	               return ResponseEntity.ok().body(updated);
	           }).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path ={"/bebida/deletar/{id}"})
	public ResponseEntity <?> delete(@PathVariable long id) {
	   return bebidarepository.findById(id)
	           .map(record -> {
	        	   bebidarepository.deleteById(id);
	               return ResponseEntity.ok().build();
	           }).orElse(ResponseEntity.notFound().build());
	}
	
}
