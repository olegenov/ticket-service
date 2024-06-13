package org.auth.services.Impl;

import org.auth.models.CustomUser;
import org.auth.models.Session;
import org.auth.repository.SessionRepository;
import org.auth.repository.UserRepository;
import org.auth.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private final SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session save(Session session) {
        return sessionRepository.save(session);
    }
}
