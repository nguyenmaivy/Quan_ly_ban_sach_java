/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.util.ArrayList;

/**
 *
 * @author MZI
 */
public interface DAOInterface<D> {
    public ArrayList<D> getALL();
    public boolean has(String D);
    public boolean add(D d);
    public boolean delete( String d);
    public boolean update(D d);
    public D getByID(String d);
    public ArrayList<D> search(String d);
        
}
