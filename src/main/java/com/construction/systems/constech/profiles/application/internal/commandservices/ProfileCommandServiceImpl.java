package com.construction.systems.constech.profiles.application.internal.commandservices;

import com.construction.systems.constech.profiles.domain.model.aggregates.Profile;
import com.construction.systems.constech.profiles.domain.model.commands.CreateProfileCommand;
import com.construction.systems.constech.profiles.domain.model.valueobjects.EmailAddress;
import com.construction.systems.constech.profiles.domain.services.ProfileCommandService;
import com.construction.systems.constech.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

/**
 * ProfileCommandServiceImpl
 *
 * <p>Service that handles the commands for profiles</p>
 */
@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileRepository profileRepository;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Long handle(CreateProfileCommand command) {
        var emailAddress = new EmailAddress(command.email());
        profileRepository.findByEmail(emailAddress).map(profile -> {
            throw new IllegalArgumentException("Profile with email " + command.email() + " already exists");
        });

        var profile = new Profile(command.firstName(), command.lastName(), command.email(), command.street(), command.number(), command.city(), command.state(), command.zipCode());
        profileRepository.save(profile);
        return profile.getId();
    }
}