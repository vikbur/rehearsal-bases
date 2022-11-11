package org.vikbur.services;

import com.google.gson.Gson;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vikbur.models.Base;
import org.vikbur.models.User;
import org.vikbur.models.requests.CreateBaseRequest;
import org.vikbur.models.responses.CreateObjectResponse;
import org.vikbur.repositories.BaseCrudRepository;
import org.vikbur.repositories.UserCrudRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaseService {
    private final Gson gson = new Gson();
    private final BaseCrudRepository baseCrudRepository;
    private final UserCrudRepository userCrudRepository;

    public String createBase(@NonNull CreateBaseRequest createBaseRequest, String userLogin) throws Exception {
        log.info(String.format("Create base '%s' attempting by '%s'", createBaseRequest.getName(), userLogin));
        validateBase(createBaseRequest);
        Base base = createBaseRequest.toBase();
        User user = userCrudRepository.findByLogin(userLogin).orElse(null);
        if (user != null){
            base.setOwner(user);
        } else {
            throw new IllegalArgumentException(String.format("User '%s' not found in data base", userLogin));
        }
        baseCrudRepository.save(base);
        return gson.toJson(new CreateObjectResponse(String.format("Base '%s' created", base.getName()),
                baseCrudRepository.findByName(base.getName()).orElse(new Base()).getId()));
    }

    public void validateBase(CreateBaseRequest request) throws IllegalArgumentException {
        if (request.getName() == null || request.getName().isEmpty()){
            throw new IllegalArgumentException("Incorrect base name");
        }
        if (baseCrudRepository.findByName(request.getName()).orElse(null) != null){
            throw new IllegalArgumentException(String.format("Base '%s' already exist", request.getName()));
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()){
            throw new IllegalArgumentException("Incorrect email");
        }
        if (request.getPhone() == null || request.getPhone().isEmpty()){
            throw new IllegalArgumentException("Incorrect phone");
        }
        if (request.getAddress() == null || request.getAddress().isEmpty()){
            throw new IllegalArgumentException("Incorrect base address");
        }
    }

    public String getBaseById(int id, String userLogin) throws Exception{
        log.info(String.format("Get base by id '%d' attempting by '%s'", id, userLogin));
        Optional<Base> base = baseCrudRepository.findById(id);
        if (base.isEmpty()){
            throw new IllegalArgumentException(String.format("Base with id '%d' not found", id));
        }
        return gson.toJson(prepareBase(base.get()));
    }

    private Base prepareBase(Base base){
        base.setRooms(base.getRooms().stream().peek(r -> r.setBase(new Base())).collect(Collectors.toSet()));
        return base;
    }
}
