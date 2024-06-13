package org.auth.services;

import org.auth.models.CustomUser;
import org.auth.models.Session;

public interface SessionService {
    Session save(Session session);
}
