package epf;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.Exception.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

/**
 * Unit test for simple App.
 */
@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientDao clientDao;

    private Client client;
    private Client illegalClient;

    @Before
    public void setUp() {
        client = new Client(1, "Jackson", "Micheal", "micheal.jackson@beat.it", LocalDate.of(1990,5,2));
        illegalClient = new Client(1,"Jackson", "Micheal Junior", "micheal.jacksonJunior@beat.it", LocalDate.of(2018,5,2));
    }

    @Test
    public void findAll_should_fail_when_dao_throws_exception() throws DaoException{
        when(this.clientDao.findAll()).thenThrow(DaoException.class);

        assertThrows(ServiceException.class, () -> clientService.findAll());
    }

    @Test
    public void create_should_return_id() throws DaoException, ServiceException {
        when(clientDao.create(client)).thenReturn(1L);
        long id = clientService.create(client);
        assertEquals(1L,id);
    }

    @Test
    public void create_should_return_minus1_with_invalid_age() throws DaoException, ServiceException {
        when(clientDao.create(client)).thenReturn(1L);
        long id = clientService.create(illegalClient);
        assertEquals(-1L,id);
    }

    @Test
    public void findClientById_should_return_client() throws DaoException, ServiceException {
        when(clientDao.findById(1L)).thenReturn(client);
        Client foundClient = clientService.findById(1L);
        assertNotNull(foundClient);
        assertEquals(client.getId(), foundClient.getId());
        assertEquals(client.getNom(), foundClient.getNom());
        assertEquals(client.getPrenom(), foundClient.getPrenom());
        assertEquals(client.getEmail(), foundClient.getEmail());
        assertEquals(client.getNaissance(), foundClient.getNaissance());
    }
}
