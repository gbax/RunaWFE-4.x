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
package ru.runa.commons.cache;

/**
 * Interface for all components, controlling cache life circle.
 * @param <CacheImpl> Cache type, controlled by component.
 */
public interface CacheControl<CacheImpl extends CacheImplementation> {
    /**
     * Create cache instance. Created cache instance must not be stored inside controlling object.
     * If system decide to make created cache as default, initCache will be called.  
     * @return Cache instance.
     */
    public CacheImpl buildCache();

    /**
     * Set default cache instance. Controlling object may return this cache instance on {@link #getCache()} calls.
     * Then cache become invalid, {@link #getCache()} must return null. 
     * @param cache
     */
    public void initCache(CacheImpl cache);

    /**
     * Return cache instance, set by previous {@link #initCache(Object)} call. 
     * Then cache instance become invalid, {@link #getCache()} must return null.
     * @return Current cache instance, controlled by this controlling object.
     */
    public CacheImpl getCache();

    /**
     * Return control object state. If exists at least one transaction, which affecting controlled cache,
     * when return true, otherwise return false.   
     * @return Control object state.
     */
    public boolean isLocked();

    /**
     * Set control object initiating flag.
     */
    public void initiateInProcess();

    /**
     * Reset control object initiating flag.
     */
    public void initiateComplete();

    /**
     * Return control object initiating flag state.
     * @return Control object initiating flag state
     */
    public boolean isInInitiate();
}
