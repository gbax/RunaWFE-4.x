/*
 * This file is part of the RUNA WFE project.
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation; version 2.1 
 * of the License. 
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU Lesser General Public License for more details. 
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package ru.runa.wfe.user.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.user.Actor;

import com.google.common.base.Objects;

/**
 * Created on 14.12.2004
 */
@Entity
@Table(name = "ACTOR_PASSWORD")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
class ActorPassword {
    private static final String DIGEST_ALGORITHM = "MD5";

    private Long actorId;

    private byte[] password;

    @SuppressWarnings("unused")
    private ActorPassword() {
    }

    public ActorPassword(Actor actor, String password) {
        setActorId(actor.getId());
        setPassword(password);
    }

    @Id
    @Column(name = "ACTOR_ID", nullable = false)
    protected Long getActorId() {
        return actorId;
    }

    private void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    /**
     * @return encrypted password for an actor
     */
    @Lob
    @Column(name = "PASSWD", nullable = false)
    protected byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public void setPassword(String password) {
        try {
            setPassword(MessageDigest.getInstance(DIGEST_ALGORITHM).digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new InternalApplicationException(e);
        }
    }

    @Override
    public int hashCode() {
        // final int prime = 31;
        // int result = 1;
        // result = prime * result + (int) (actorId ^ (actorId >>> 32));
        // result = prime * result + Arrays.hashCode(password);
        return Objects.hashCode(actorId, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ActorPassword other = (ActorPassword) obj;
        return Objects.equal(actorId, other.actorId) && Arrays.equals(password, other.password);
    }
}
