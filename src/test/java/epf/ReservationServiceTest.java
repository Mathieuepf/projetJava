package epf;

import com.epf.rentmanager.Exception.DaoException;
import com.epf.rentmanager.Exception.ServiceException;
import com.epf.rentmanager.dao.ReservationDao;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationDao reservationDao;

    private Reservation reservation;

    @Before
    public void setUp() {
        reservation = new Reservation(1,1,1,LocalDate.of(2024,5,3),LocalDate.of(2024,5,5));
    }

    @Test
    public void create_should_fail_when_dao_throws_exception() throws DaoException {
        when(this.reservationDao.create(reservation)).thenThrow(DaoException.class);

        assertThrows(ServiceException.class, () -> reservationService.create(reservation));
    }

    @Test
    public void create_should_return_minus1_with_invalid_duration() throws DaoException, ServiceException {
        when(reservationDao.create(new Reservation(1,1,1,LocalDate.of(2024,5,3),LocalDate.of(2024,6,18)))).thenReturn(1L);
        long id = reservationService.create(new Reservation(1,1,1,LocalDate.of(2024,5,3),LocalDate.of(2024,6,18)));
        assertEquals(-1L,id);
    }

    @Test
    public void create_should_return_id() throws DaoException, ServiceException {
        when(reservationDao.create(reservation)).thenReturn(1L);
        long id = reservationService.create(reservation);
        assertEquals(1L,id);
    }

    @Test
    public void create_should_return_minus1_with_debut_is_after_fin() throws DaoException, ServiceException {
        when(reservationDao.create(new Reservation(1,1,1,LocalDate.of(2024,5,3),LocalDate.of(2024,5,1)))).thenReturn(1L);
        long id = reservationService.create(new Reservation(1,1,1,LocalDate.of(2024,5,3),LocalDate.of(2024,5,1)));
        assertEquals(-1L,id);
    }
}
