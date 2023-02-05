package api.services;

import api.exceptions.NotFoundException;
import api.models.User;
import api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceTest {
    @Mock
    private final UserRepository repository;
    private UserService service;

    @Autowired
    public UserServiceTest(UserRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        this.service = new UserService(this.repository);
    }

    private User buildPayloadUser(Long id, String name, String email, String password) {
        return User
                .builder()
                .id(id)
                .name(name)
                .email(email)
                .password(password)
                .build();
    }

    @Test
    void create_should_call_save_method_of_user_repository() {
        User payload = this.buildPayloadUser(null, "test test", "test@test.com", "test_test");
        this.service.create(payload);
        verify(this.repository, times(1)).save(payload);
    }

    @Test
    void create_should_set_payload_id_to_null_before_call_save_method_of_user_repository() {
        User payload = this.buildPayloadUser(1L, "test test", "test@test.com", "test_test");
        this.service.create(payload);
        verify(this.repository, times(1))
                .save(this.buildPayloadUser(null, "test test", "test@test.com", "test_test"));
    }

    @Test
    void create_should_return_the_result_of_save_method_of_user_repository() {
        User payload = this.buildPayloadUser(null, "test test", "test@test.com", "test_test");
        User expected = this.buildPayloadUser(1L, "test test", "test@test.com", "test_test");
        when(this.repository.save(payload)).thenReturn(expected);
        User result = this.service.create(payload);
        assertEquals(expected, result);
    }

    @Test
    void read_should_call_find_by_id_method_of_user_repository() {
        User payload = this.buildPayloadUser(1L, "test test", "test@test.com", "test_test");
        when(this.repository.findById(payload.getId())).thenReturn(Optional.of(payload));
        this.service.read(payload.getId());
        verify(this.repository, times(1)).findById(payload.getId());
    }

    @Test
    void read_should_throw_an_exception_when_the_requested_user_does_not_exists() {
        Long payload = 1L;
        when(this.repository.findById(payload)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> this.service.read(payload))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Desculpe, não foi possível encontrar o usuário procurado. Verifique se as informações estão corretas e tente novamente.");
    }

    @Test
    void read_should_return_the_result_of_find_by_id_method_of_user_repository_when_the_requested_user_exists() {
        User payload = this.buildPayloadUser(1L, "test test", "test@test.com", "test_test");
        when(this.repository.findById(payload.getId())).thenReturn(Optional.of(payload));
        User result = this.service.read(payload.getId());
        assertEquals(payload, result);
    }

    @Test
    void update_should_call_exists_by_id_method_of_user_repository() {
        User payload = this.buildPayloadUser(1L, "test test", "test@test.com", "test_test");
        when(this.repository.existsById(payload.getId())).thenReturn(Boolean.TRUE);
        this.service.update(payload);
        verify(this.repository, times(1)).existsById(payload.getId());
    }

    @Test
    void update_should_throw_an_exception_when_the_requested_user_does_not_exists() {
        User payload = this.buildPayloadUser(1L, "test test", "test@test.com", "test_test");
        when(this.repository.existsById(payload.getId())).thenReturn(Boolean.FALSE);
        assertThatThrownBy(() -> this.service.update(payload))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Desculpe, não foi possível encontrar o usuário procurado. Verifique se as informações estão corretas e tente novamente.");
    }

    @Test
    void update_should_call_save_method_of_user_repository_when_the_requested_user_exists() {
        User payload = this.buildPayloadUser(1L, "test test", "test@test.com", "test_test");
        when(this.repository.existsById(payload.getId())).thenReturn(Boolean.TRUE);
        this.service.update(payload);
        verify(this.repository, times(1)).save(payload);
    }

    @Test
    void update_should_return_the_result_of_save_method_of_user_repository_when_the_requested_user_exists() {
        User payload = this.buildPayloadUser(1L, "test test", "test@test.com", "test_test");
        when(this.repository.existsById(payload.getId())).thenReturn(Boolean.TRUE);
        when(this.repository.save(payload)).thenReturn(payload);
        User result = this.service.update(payload);
        assertEquals(payload, result);
    }

    @Test
    void delete_should_call_exists_by_id_method_of_user_repository() {
        Long payload = 1L;
        when(this.repository.existsById(payload)).thenReturn(Boolean.TRUE);
        this.service.delete(payload);
        verify(this.repository, times(1)).existsById(payload);
    }

    @Test
    void delete_should_throw_an_exception_when_the_requested_user_does_not_exists() {
        Long payload = 1L;
        when(this.repository.existsById(payload)).thenReturn(Boolean.FALSE);
        assertThatThrownBy(() -> this.service.delete(payload))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Desculpe, não foi possível encontrar o usuário procurado. Verifique se as informações estão corretas e tente novamente.");
    }

    @Test
    void delete_should_call_delete_by_id_method_of_user_repository_when_the_requested_user_exists() {
        Long payload = 1L;
        when(this.repository.existsById(payload)).thenReturn(Boolean.TRUE);
        this.service.delete(payload);
        verify(this.repository, times(1)).deleteById(payload);
    }
}
