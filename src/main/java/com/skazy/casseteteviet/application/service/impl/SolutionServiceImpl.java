package com.skazy.casseteteviet.application.service.impl;

import com.google.gson.Gson;
import com.skazy.casseteteviet.application.service.SolutionService;
import com.skazy.casseteteviet.infrastructure.dto.ListeSolutionsDto;
import com.skazy.casseteteviet.infrastructure.dto.SolutionDto;
import com.skazy.casseteteviet.infrastructure.entite.Solution;
import com.skazy.casseteteviet.infrastructure.exception.*;
import com.skazy.casseteteviet.infrastructure.mapper.SolutionMapper;
import com.skazy.casseteteviet.infrastructure.repository.SolutionRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

@Data
@Service
public class SolutionServiceImpl implements SolutionService {

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    SolutionMapper solutionMapper;

    boolean solutionDejaGenere = false;

    Calcul calcule = new Calcul();

    String MESSAGE_OK = "la sauvegarde à bien été prise en compte, statut de la solution: ";

    Gson gson = new Gson();

    /**
     * Recupère une solution possèdant l'id donné en entrée
     *
     * @param id Long
     * @return SolutionDto
     */
    public ResponseEntity<SolutionDto> getSolution(Long id) {
        SolutionDto solutionDto = solutionMapper.solutionToSolutionDto(solutionRepository.findOneById(id));
        if (solutionDto == null) {
            return new ResponseEntity<>(new SolutionDto(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(solutionDto, HttpStatus.OK);
    }

    /**
     * Recupère toutes les solutions en Base
     *
     * @return ListeSolutionsDto
     */
    public ListeSolutionsDto getSolutions() {
        List<Solution> solutions = solutionRepository.findAll();
        return new ListeSolutionsDto(solutionMapper.listeSolutionToListeSolutionDto(solutions), calcule.getTempsDecalcul());
    }

    /**
     * Supprime une Solutioon spécifique
     *
     * @param id Long
     */
    public void deleteSolution(Long id) {
        solutionRepository.deleteById(id);
    }

    /**
     * Supprime toutes les solutions en Base
     */
    public void deleteSolutions() {
        solutionRepository.deleteAll();
        solutionDejaGenere = false;
    }

    /**
     * Fait les verifications nécéssaires et modifie la solution en Base
     *
     * @param solutionDto SolutionDto
     * @return ResponseEntity<String>
     * @throws Exception e
     */
    public ResponseEntity<String> saveSolution(SolutionDto solutionDto) throws Exception {
        Solution solution;
        try {
            solution = solutionMapper.solutionDtoToSolution(solutionDto);
            verifierLaSolution(solution);
            estNouvelle(solution);
            solution.setStatut(estCorrecte(solution));
            solutionRepository.save(solution);

        } catch (ValeurDupliqueeException valeurDupliqueeException) {
            return ResponseEntity.badRequest().body(gson.toJson(valeurDupliqueeException.getMessage()));
        } catch (FormatSolutionException formatSolutionException) {
            return ResponseEntity.badRequest().body(gson.toJson(formatSolutionException.getMessage()));
        } catch (SolutionNouvelleException solutionNouvelleException) {
            return ResponseEntity.badRequest().body(gson.toJson(solutionNouvelleException.getMessage()));
        } catch (NombreDeValeursException nombreDeValeursException) {
            return ResponseEntity.badRequest().body(gson.toJson(nombreDeValeursException.getMessage()));
        } catch (NombreAutoriseException nombreAutoriseException) {
            return ResponseEntity.badRequest().body(gson.toJson(nombreAutoriseException.getMessage()));
        }
        return new ResponseEntity<>(gson.toJson(MESSAGE_OK+solution.statutToString()), HttpStatus.OK);
    }

    /**
     * Permet de savoir si la solution est Correcte
     * @param solution solution
     * @return boolean
     */
    private boolean estCorrecte(Solution solution) {
        String[] valeurs = solution.getValeurs().split(",");
        int[] listeValeurs = new int[9];
            int i = 0;
            for(String c : valeurs){
                listeValeurs[i] = Integer.parseInt(c);
                i += 1;
            }
        return Calcul.caclulEgale66(listeValeurs);
    }

    /**
     * Verifie si la liste de valeur envoyé existe déjà en base.
     *
     * @param solution Solution
     * @throws SolutionNouvelleException e
     */
    private void estNouvelle(Solution solution) throws SolutionNouvelleException {
        if (!solutionRepository.existsSolutionById(solution.getId())) {
            throw new SolutionNouvelleException();
        }
    }

    /**
     * Fait toutes les verification concernant le format, la detection de doublons et le nombre de valeurs utilisé
     *
     * @param solution Solution
     * @throws Exception e
     */
    private void verifierLaSolution(Solution solution) throws Exception {
        String[] str = solution.getValeurs().split(",");
        if (str.length != 9) {
            throw new NombreDeValeursException();
        }
        HashSet<String> listValeurs = new HashSet<>();
        Pattern p = Pattern.compile("[1-9]");
        for (String s : str) {
            //on verifie que la valeur n'est pas vide ou null
            if (s != null && !s.isEmpty()) {
                if (p.matcher(s).matches()) {
                    // on verifie que la valeur qu'on insère n'existe pas déjà
                    if (!listValeurs.add(s)) {
                        throw new ValeurDupliqueeException();
                    }
                } else {
                    throw new NombreAutoriseException();
                }

            } else {
                throw new FormatSolutionException();
            }
        }
    }

    /**
     * Calcul toutes les solutions possibles, les sauvegardes en base et renvoit le temps de calcul.
     *
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> caclulerSolution() {
        solutionRepository.deleteAll();
        List<Solution> listeSolutions = calcule.calculerSolutions();
        solutionRepository.saveAll(listeSolutions);
        solutionDejaGenere = true;
        return new ResponseEntity<>(gson.toJson(calcule.getTempsDecalcul().toString()), HttpStatus.OK);
    }

    /**
     * Méthode permettant de recuperer le temps de calcul de la derniere execution
     *
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> getTempsDecalcul() {
        return new ResponseEntity<>(gson.toJson(calcule.getTempsDecalcul().toString()), HttpStatus.OK);
    }

    @Override
    public boolean solutionDejaGener() {
        return solutionDejaGenere;
    }
}

