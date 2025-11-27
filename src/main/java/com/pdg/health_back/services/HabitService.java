package com.pdg.health_back.services;

import com.pdg.health_back.entities.Categoria;
import com.pdg.health_back.entities.EstadoHabito;
import com.pdg.health_back.entities.Habito;
import com.pdg.health_back.entities.HabitoContador;
import com.pdg.health_back.entities.HabitoTemporizado;
import com.pdg.health_back.entities.TipoHabito;
import com.pdg.health_back.models.CategoriaRequest;
import com.pdg.health_back.models.especialHabits.DuracionRequest;
import com.pdg.health_back.models.especialHabits.DuracionResponse;
import com.pdg.health_back.models.especialHabits.RepeticionesRequest;
import com.pdg.health_back.models.especialHabits.RepeticionesResponse;
import com.pdg.health_back.models.habits.HabitRequest;
import com.pdg.health_back.models.habits.HabitResponse;
import com.pdg.health_back.repositories.CategoriaRepository;
import com.pdg.health_back.repositories.EstadoHabitoRepository;
import com.pdg.health_back.repositories.HabitRepository;
import com.pdg.health_back.repositories.HabitoContadorRepository;
import com.pdg.health_back.repositories.HabitoTemporizadoRepository;
import com.pdg.health_back.repositories.TipoHabitoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HabitService {

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private HabitoContadorRepository habitoContadorRepository;

    @Autowired
    private HabitoTemporizadoRepository habitoTemporizadoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private EstadoHabitoRepository estadoHabitoRepository;

    @Autowired
    private TipoHabitoRepository tipoHabitoRepository;

    public List<HabitResponse> getAllHabits() {
        List<Habito> habitos = habitRepository.findAll();
        return habitos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public HabitResponse getHabitById(Integer id) {
        Optional<Habito> habito = habitRepository.findById(id);
        if (habito.isPresent()) {
            return mapToResponse(habito.get());
        }
        return null;
    }

    @Transactional
    public HabitResponse createHabit(HabitRequest request, Integer idUsuario) {
        Habito habito = mapToEntity(request, idUsuario);
        // Set categorias if provided
        if (request.getCategorias() != null && !request.getCategorias().isEmpty()) {
            Set<Categoria> categorias = new HashSet<>();
            for (CategoriaRequest categoriaRequest : request.getCategorias()) {
                Categoria categoria = mapCategoriaRequestToEntity(categoriaRequest, idUsuario);
                if (categoria.getIdCategoria() != null) {
                    Optional<Categoria> existingCategoria = categoriaRepository.findById(categoria.getIdCategoria());
                    if (existingCategoria.isPresent()) {
                        categorias.add(existingCategoria.get());
                    }
                } else {
                    // If new categoria, save it first
                    Categoria savedCategoria = categoriaRepository.save(categoria);
                    categorias.add(savedCategoria);
                }
            }
            habito.setCategorias(categorias);
        }
        
        Habito savedHabito = habitRepository.save(habito);

        // Create type-specific objects based on tipo
        if (request.getIdTipo() == 2 && request.getRepeticiones() != null) {
            // Tipo Contador
            HabitoContador habitoContador = mapHabitoContadorRequestToEntity(
                request.getRepeticiones(), savedHabito);
                habitoContadorRepository.save(habitoContador);
            } else if (request.getIdTipo() == 3 && request.getDuracion() != null) {
            // Tipo Temporizado
            HabitoTemporizado habitoTemporizado = mapHabitoTemporizadoRequestToEntity(
                request.getDuracion(), savedHabito);
            habitoTemporizadoRepository.save(habitoTemporizado);
        }
        
        return mapToResponse(savedHabito);
    }

    @Transactional
    public HabitResponse updateHabit(Integer id, HabitRequest request, Integer idUsuario) {
        Optional<Habito> habitoOpt = habitRepository.findById(id);
        if (habitoOpt.isPresent()) {
            Habito habito = habitoOpt.get();
            Integer oldTipo = habito.getIdTipo();
            Integer newTipo = request.getIdTipo();
            
            updateEntityFromRequest(habito, request);
            
            // Update categorias if provided
            if (request.getCategorias() != null) {
                Set<Categoria> categorias = new HashSet<>();
                for (CategoriaRequest categoriaRequest : request.getCategorias()) {
                    Categoria categoria = mapCategoriaRequestToEntity(categoriaRequest, idUsuario);
                    if (categoria.getIdCategoria() != null) {
                        Optional<Categoria> existingCategoria = categoriaRepository.findById(categoria.getIdCategoria());
                        if (existingCategoria.isPresent()) {
                            categorias.add(existingCategoria.get());
                        }
                    } else {
                        // If new categoria, save it first
                        Categoria savedCategoria = categoriaRepository.save(categoria);
                        categorias.add(savedCategoria);
                    }
                }
                habito.setCategorias(categorias);
            }
            
            Habito updatedHabito = habitRepository.save(habito);
            
            // Handle type changes
            if (newTipo != null && !newTipo.equals(oldTipo)) {
                // Delete old type-specific objects if they exist
                if (oldTipo == 2) {
                    // Delete HabitoContador if it exists
                    Optional<HabitoContador> existingContador = 
                        habitoContadorRepository.findByHabitoId(id);
                    existingContador.ifPresent(habitoContadorRepository::delete);
                } else if (oldTipo == 3) {
                    // Delete HabitoTemporizado if it exists
                    Optional<HabitoTemporizado> existingTemporizado = 
                        habitoTemporizadoRepository.findByHabitoId(id);
                    existingTemporizado.ifPresent(habitoTemporizadoRepository::delete);
                }
                
                // Create new type-specific objects if needed
                if (newTipo == 2 && request.getRepeticiones() != null) {
                    // Create HabitoContador
                    HabitoContador habitoContador = mapHabitoContadorRequestToEntity(
                        request.getRepeticiones(), updatedHabito);
                        habitoContadorRepository.save(habitoContador);
                } else if (newTipo == 3 && request.getDuracion() != null) {
                    // Create HabitoTemporizado
                    HabitoTemporizado habitoTemporizado = mapHabitoTemporizadoRequestToEntity(
                        request.getDuracion(), updatedHabito);
                    habitoTemporizadoRepository.save(habitoTemporizado);
                }
            } else if (newTipo != null && newTipo.equals(oldTipo)) {
                // Update existing type-specific objects if tipo hasn't changed
                if (newTipo == 2 && request.getRepeticiones() != null) {
                    Optional<HabitoContador> existingContador = 
                        habitoContadorRepository.findByHabitoId(id);
                    if (existingContador.isPresent()) {
                        HabitoContador contador = existingContador.get();
                        contador.setRepeticionesObjetivo(request.getRepeticiones().getRepeticionesObjetivo());
                        habitoContadorRepository.save(contador);
                    }
                } else if (newTipo == 3 && request.getDuracion() != null) {
                    Optional<HabitoTemporizado> existingTemporizado = 
                        habitoTemporizadoRepository.findByHabitoId(id);
                    if (existingTemporizado.isPresent()) {
                        HabitoTemporizado temporizado = existingTemporizado.get();
                        temporizado.setDuracionObjetivo(request.getDuracion().getDuracionObjetivo());
                        habitoTemporizadoRepository.save(temporizado);
                    }
                }
            }
            
            return mapToResponse(updatedHabito);
        }
        return null;
    }

    public boolean deleteHabit(Integer id) {
        if (habitRepository.existsById(id)) {
            habitRepository.deleteById(id);
            return true;
        }
        return false;
    }


    private HabitResponse mapToResponse(Habito habito) {
        HabitResponse response = new HabitResponse();
        response.setIdHabito(habito.getIdHabito());
        response.setCategorias(habito.getCategorias());
        response.setNombre(habito.getNombre());
        response.setDescripcion(habito.getDescripcion());
        response.setFechaInicio(habito.getFechaInicio());
        response.setFechaFin(habito.getFechaFin());
        response.setRecordatorio(habito.getRecordatorio());
        
        // Set estado name
        Optional<EstadoHabito> estadoOpt = estadoHabitoRepository.findById(habito.getIdEstado());
        estadoOpt.ifPresent(estado -> response.setEstado(estado.getNombre()));
        
        // Set tipo name
        Optional<TipoHabito> tipoOpt = tipoHabitoRepository.findById(habito.getIdTipo());
        tipoOpt.ifPresent(tipo -> response.setTipo(tipo.getNombre()));
        
        // Set type-specific objects if they exist
        if (habito.getIdTipo() == 2) {
            habitoContadorRepository.findByHabitoId(habito.getIdHabito()).ifPresent(contador -> {
                RepeticionesResponse rep = new RepeticionesResponse();
                rep.setIdHabito(contador.getIdHabito());
                rep.setRepeticionesObjetivo(contador.getRepeticionesObjetivo());
                rep.setRepeticionesLogradas(contador.getRepeticionesLogradas());
                response.setHabitoContador(rep);
            });
        } else if (habito.getIdTipo() == 3) {
            habitoTemporizadoRepository.findByHabitoId(habito.getIdHabito()).ifPresent(temp -> {
                DuracionResponse dur = new DuracionResponse();
                dur.setIdHabito(temp.getIdHabito());
                dur.setDuracionObjetivo(temp.getDuracionObjetivo());
                dur.setTiempoLogrado(temp.getTiempoLogrado());
                response.setHabitoTemporizado(dur);
            });
        }
        
        return response;
    }

    private Categoria mapCategoriaRequestToEntity (CategoriaRequest request, Integer id_usuario){
        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        categoria.setColor(request.getColor());
        categoria.setIdUsuario(id_usuario);
        return categoria;
    }

    private HabitoContador mapHabitoContadorRequestToEntity(RepeticionesRequest request, Habito habito) {
        HabitoContador habitoContador = new HabitoContador();
        habitoContador.setHabito(habito);
        habitoContador.setRepeticionesObjetivo(request.getRepeticionesObjetivo());
        habitoContador.setRepeticionesLogradas(
            request.getRepeticionesLogradas() != null ? request.getRepeticionesLogradas() : 0);
        return habitoContador;
    }

    private HabitoTemporizado mapHabitoTemporizadoRequestToEntity(DuracionRequest request, Habito habito) {
        HabitoTemporizado habitoTemporizado = new HabitoTemporizado();
        habitoTemporizado.setHabito(habito);
        habitoTemporizado.setDuracionObjetivo(request.getDuracionObjetivo());
        habitoTemporizado.setTiempoLogrado(
            request.getTiempoLogrado() != null ? request.getTiempoLogrado() : 0);
        return habitoTemporizado;
    }   

    private Habito mapToEntity(HabitRequest request, Integer idUsuario) {
        Habito habito = new Habito();
        habito.setIdUsuario(idUsuario);
        habito.setNombre(request.getNombre());
        habito.setDescripcion(request.getDescripcion());
        habito.setFechaInicio(request.getFechaInicio());
        habito.setFechaFin(request.getFechaFin());
        habito.setRecordatorio(request.getRecordatorio());
        habito.setIdTipo(request.getIdTipo());
        habito.setIdEstado(1); // Default estado "activo"
        return habito;
    }

    private void updateEntityFromRequest(Habito habito, HabitRequest request) {
        if (request.getNombre() != null) {
            habito.setNombre(request.getNombre());
        }
        if (request.getDescripcion() != null) {
            habito.setDescripcion(request.getDescripcion());
        }
        if (request.getFechaInicio() != null) {
            habito.setFechaInicio(request.getFechaInicio());
        }
        if (request.getFechaFin() != null) {
            habito.setFechaFin(request.getFechaFin());
        }
        if (request.getRecordatorio() != null) {
            habito.setRecordatorio(request.getRecordatorio());
        }
        if (request.getIdTipo() != null) {
            habito.setIdTipo(request.getIdTipo());
        }
        if (request.getIdEstado() != null) {
            habito.setIdEstado(request.getIdEstado());
        }
    }
}
