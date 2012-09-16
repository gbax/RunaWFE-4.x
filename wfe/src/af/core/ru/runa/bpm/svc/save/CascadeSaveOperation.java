/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package ru.runa.bpm.svc.save;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import ru.runa.bpm.graph.exe.ProcessInstance;

public class CascadeSaveOperation implements SaveOperation {
    private static Log log = LogFactory.getLog(CascadeSaveOperation.class);

    @Override
    public void save(HibernateTemplate hibernateTemplate, ProcessInstance processInstance) {
        log.debug("cascading save of '" + processInstance + "'");
        Set cascadedProcessInstances = new HashSet();
        cascadedProcessInstances.add(processInstance);
        cascadeSave(processInstance.removeCascadeProcessInstances(), hibernateTemplate, cascadedProcessInstances);
    }

    private void cascadeSave(Collection cascadeProcessInstances, HibernateTemplate hibernateTemplate, Set cascadedProcessInstances) {
        if (cascadeProcessInstances != null) {
            Iterator iter = cascadeProcessInstances.iterator();
            while (iter.hasNext()) {
                ProcessInstance cascadeInstance = (ProcessInstance) iter.next();
                saveCascadeInstance(cascadeInstance, hibernateTemplate, cascadedProcessInstances);
            }
        }
    }

    private void saveCascadeInstance(ProcessInstance cascadeInstance, HibernateTemplate hibernateTemplate, Set cascadedProcessInstances) {
        if (!cascadedProcessInstances.contains(cascadeInstance)) {
            Collection cascadeProcessInstances = cascadeInstance.removeCascadeProcessInstances();
            log.debug("cascading save to process instance '" + cascadeInstance + "'");
            hibernateTemplate.save(cascadeInstance);
            cascadedProcessInstances.add(cascadeInstance);
            cascadeSave(cascadeProcessInstances, hibernateTemplate, cascadedProcessInstances);
        }
    }

}
