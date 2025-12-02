/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resetasmedicasnosql.service;

import com.mycompany.resetasmedicasnosql.exception.ServiceException;
import com.mycompany.resetasmedicasnosql.model.Administrador;
import com.mycompany.resetasmedicasnosql.repository.IAdministradorRepository;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

/**
 *
 * @author Luis Valenzuela
 */
public class AdministradorService {
    private IAdministradorRepository administradorRepository;

    public AdministradorService(IAdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    public ObjectId crearAdmin(Administrador admin) throws ServiceException {
        try {
            if (administradorRepository.findByEmail(admin.getEmail()).isPresent()) {
                throw new ServiceException("El email ya está registrado");
            }
            return administradorRepository.create(admin);
        } catch (Exception e) {
            throw new ServiceException("Error al crear administrador", e);
        }
    }

    public Administrador autenticarAdmin(String email, String contraseña) throws ServiceException {
        try {
            Optional<Administrador> admin = administradorRepository.findByEmail(email);
            if (admin.isEmpty()) {
                throw new ServiceException("Administrador no encontrado");
            }
            Administrador a = admin.get();
            if (!a.isActivo()) {
                throw new ServiceException("Cuenta de administrador desactivada");
            }
            if (!a.getContraseña().equals(contraseña)) {
                throw new ServiceException("Contraseña incorrecta");
            }
            return a;
        } catch (Exception e) {
            throw new ServiceException("Error en autenticación", e);
        }
    }

    public Administrador obtenerAdmin(ObjectId id) throws ServiceException {
        try {
            Optional<Administrador> admin = administradorRepository.findById(id);
            if (admin.isEmpty()) {
                throw new ServiceException("Administrador no encontrado");
            }
            return admin.get();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener administrador", e);
        }
    }

    public List<Administrador> obtenerTodosAdmins() throws ServiceException {
        try {
            return administradorRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error al obtener administradores", e);
        }
    }

    public boolean asignarPermisos(ObjectId idAdmin, List<String> permisos) throws ServiceException {
        try {
            Optional<Administrador> admin = administradorRepository.findById(idAdmin);
            if (admin.isEmpty()) {
                throw new ServiceException("Administrador no encontrado");
            }
            Administrador a = admin.get();
            a.setPermisos(permisos);
            return administradorRepository.update(a);
        } catch (Exception e) {
            throw new ServiceException("Error al asignar permisos", e);
        }
    }

    public boolean verificarPermiso(ObjectId idAdmin, String permiso) throws ServiceException {
        try {
            Optional<Administrador> admin = administradorRepository.findById(idAdmin);
            if (admin.isEmpty()) {
                return false;
            }
            Administrador a = admin.get();
            return a.getPermisos() != null && a.getPermisos().contains(permiso);
        } catch (Exception e) {
            throw new ServiceException("Error al verificar permiso", e);
        }
    }
}
