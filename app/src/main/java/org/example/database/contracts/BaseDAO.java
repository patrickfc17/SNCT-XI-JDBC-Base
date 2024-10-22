package org.example.database.contracts;

import java.util.List;

import org.example.records.UserDTO;

public interface BaseDAO {
    public List<UserDTO> all();

    public UserDTO find(Integer id);

    public void create(UserDTO user);

    public void update(UserDTO user);

    public void delete(Integer id);
}
