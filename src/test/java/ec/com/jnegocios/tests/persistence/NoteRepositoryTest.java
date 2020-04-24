package ec.com.jnegocios.tests.persistence;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ec.com.jnegocios.entity.Note;
import ec.com.jnegocios.repository.NoteRepository;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NoteRepositoryTest {
	
	@Autowired
	private NoteRepository repoNote;
	
	@Test
	public void findAllTest ()
	{
		Collection<Note> notes = repoNote.findAll();
		
		LoggerFactory.getLogger(getClass()).info(notes.toString());
		
		assertThat(notes.size()).isEqualTo(4);
	}
}
