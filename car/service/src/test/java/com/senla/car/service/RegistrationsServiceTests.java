package com.senla.car.service;

import com.senla.car.dao.RegistrationsRepository;
import com.senla.car.dto.RegistrationDto;
import com.senla.car.model.Registration;
import com.senla.car.service.impl.RegistrationsServiceImpl;
import com.senla.car.service.mappers.RegistrationsMapper;
import com.senla.common.exception.repository.DeleteStatementRepositoryException;
import com.senla.common.exception.repository.EntityNotFoundException;
import com.senla.common.exception.repository.InsertStatementRepositoryException;
import com.senla.common.exception.repository.UpdateStatementRepositoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RegistrationsServiceTests {

    @Spy
    private final RegistrationsMapper registrationsMapper = Mappers.getMapper(RegistrationsMapper.class);

    @Mock
    private RegistrationsRepository registrationsRepository;

    @InjectMocks
    private final RegistrationsService registrationsService = new RegistrationsServiceImpl();

    @Test
    public void insertTest() {
        RegistrationDto registrationDto =
                new RegistrationDto(
                        1L,
                        "123",
                        "mod-1",
                        2000,
                        "1212",
                        1000,
                        2000,
                        null,
                        null
                );
        Mockito.lenient()
                .when(registrationsRepository.insert(Mockito.any(Registration.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(registrationDto, registrationsService.insert(registrationDto));
    }

    @Test
    public void insertNullTest() {
        Mockito.lenient()
                .when(registrationsRepository.insert(Mockito.nullable(Registration.class)))
                .thenThrow(InsertStatementRepositoryException.class);

        Assertions.assertThrows(InsertStatementRepositoryException.class,
                () -> registrationsService.insert(null));
    }

    @Test
    public void updateTest() {
        RegistrationDto registrationDto =
                new RegistrationDto(
                        1L, 
                        "123", 
                        "mod-1", 
                        2000,
                        "1212", 
                        1000, 
                        2000,
                        null, 
                        null
                );

        Mockito.lenient()
                .when(registrationsRepository.update(Mockito.any(Registration.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(registrationDto, registrationsService.update(registrationDto));
    }

    @Test
    public void updateNotExistedTest() {
        RegistrationDto registrationDto = new RegistrationDto(
                2L,
                "234",
                "mod-2",
                2000,
                "1212",
                1000,
                2000,
                null,
                null
        );
        Mockito.when(registrationsRepository.update(Mockito.any(Registration.class))).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> registrationsService.update(registrationDto));
    }

    @Test
    public void updateNullTest() {
        Mockito.lenient()
                .when(registrationsRepository.update(Mockito.nullable(Registration.class)))
                .thenThrow(UpdateStatementRepositoryException.class);

        Assertions.assertThrows(UpdateStatementRepositoryException.class,
                () -> registrationsService.update(null));
    }

    @Test
    public void deleteTest() {
        Mockito.lenient().when(registrationsRepository.delete(1L)).thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(1, registrationsService.delete(1L));
    }

    @Test
    public void deleteNotExistedTest() {
        Mockito.lenient().when(registrationsRepository.delete(Mockito.anyLong())).thenThrow(DeleteStatementRepositoryException.class);

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> registrationsService.delete(1L));
    }

    @Test
    public void deleteNullTest() {
        Mockito.lenient()
                .when(registrationsRepository.delete(Mockito.nullable(Long.class)))
                .thenThrow(DeleteStatementRepositoryException.class);

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> registrationsService.delete(null));
    }

    @Test
    public void selectTest() {
        Registration registration = new Registration(
                1L,
                "123",
                "mod-1",
                2000,
                "1212",
                1000,
                2000,
                null,
                null
        );
        Mockito.when(registrationsRepository.select(1L)).thenReturn(registration);

        Assertions.assertEquals(registrationsService.select(1L), registrationsMapper.mapToDto(registration));
    }

    @Test
    public void selectNotExistedTest() {
        Mockito.lenient().when(registrationsRepository.select(Mockito.anyLong())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> registrationsService.select(1L));
    }

    @Test
    public void selectNullTest() {
        Mockito.lenient().when(registrationsRepository.select(Mockito.nullable(Long.class))).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> registrationsService.select(null));
    }

    @Test
    public void selectAllTest() {
        List<Registration> registrations = new ArrayList<>();
        registrations.add(new Registration(
                1L,
                "123",
                "mod-1",
                2000,
                "1212",
                1000,
                2000,
                null,
                null
        ));
        registrations.add(new Registration(
                2L,
                "234",
                "mod-2",
                2002,
                "2323",
                1100,
                1900,
                null,
                null
        ));

        Mockito.lenient().when(registrationsRepository.selectAll()).thenReturn(registrations);

        List<RegistrationDto> registrationDtos = registrationsService.selectAll();
        Assertions.assertEquals(2, registrationDtos.size());
        for (int i = 0; i < registrationDtos.size(); i++) {
            Assertions.assertEquals(registrationDtos.get(i), registrationsMapper.mapToDto(registrations.get(i)));
        }
    }
    

}
