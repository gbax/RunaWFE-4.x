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
package ru.runa.wf.periodic;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ru.runa.wf.logic.JbpmExecutionLogic;

/**
 * Removes unused timers.
 * @author Konstantinov Aleksey
 */
@Transactional
public class TimersRemover extends TimerTask {
    // TODO join with JobFinder
    @Autowired
    private JbpmExecutionLogic executionLogic;
    
    @Override
    public void run() {
        executionLogic.removeUnnecessaryTimers();
    }

}
