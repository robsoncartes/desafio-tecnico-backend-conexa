package br.com.conexasaude.services;

import br.com.conexasaude.models.TestExampleEntity;
import br.com.conexasaude.repositories.ExampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBServiceImpl {

    @Autowired
    private ExampleRepository exampleRepository;

    public void instantiateTestDatabase() {

        TestExampleEntity example1 = new TestExampleEntity(1L, "It's just a simple test.");
        TestExampleEntity example2 = new TestExampleEntity(2L, "It's just another simple test.");

        exampleRepository.save(example1);
        exampleRepository.save(example2);
    }
}
