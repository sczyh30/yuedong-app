package com.m1racle.yuedong.dao;

import com.m1racle.yuedong.entity.Drink;

/**
 * Yuedong app
 * Drink Dao Service Impl
 * @author sczyh30
 * @since v1.40
 */
public class DrinkDao extends BaseDaoImpl implements BaseDao<Drink> {

    @Override
    public boolean save(Drink drink) {
        return false;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public Drink get(int id) {
        return null;
    }

    @Override
    public boolean update(Drink drink, int id) {
        return false;
    }
}
