package lt.itakademija.electors.district;

import lt.itakademija.exceptions.DistrictCloneException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pavel on 2017-01-14.
 */
@Service
public class DistrictService {

    @Autowired
    DistrictRepository repository;

    @Autowired
    DistrictPagingAndSortingRepository pageRepository;

    public List<DistrictReport> getDistrictsList() {
        List<DistrictEntity> list = repository.findAll();
        return mappingToReport(list);
    }

    @Transactional
    public DistrictEntity save(DistrictEntity apylinke){
    	List<DistrictEntity> findByNameAndAdress = repository.findByNameAndAdress(apylinke);
		if (findByNameAndAdress.size() == 0) {
            return repository.save(apylinke);
        }
		else {
			if(findByNameAndAdress.get(0).getId()== apylinke.getId()){
				return repository.save(apylinke);
			}
		}
        System.out.println("\n"+findByNameAndAdress.get(0).getName() + " " + findByNameAndAdress.get(0).getCounty().getName());
        throw new DistrictCloneException("This district is already registered " + apylinke.getName() +" county "+ apylinke.getCounty().getName());
    }

    @Transactional
    public boolean delete(Long id) {
        repository.delete(id);
        return true;
    }

    public List getDistrictsWithNullRepresentativesList() {
        List<DistrictEntity> list = repository.findAll()
                .stream()
                .filter(d -> d.getRepresentative() == null)
                .collect(Collectors.toList());
        return mappingToReport(list);
    }

	public DistrictReport getDistrictById(Long id) {
		DistrictEntity district = repository.findById(id);
		DistrictReport report = new DistrictReport(district);
		return report;
	}

    private List<DistrictReport> mappingToReport(List<DistrictEntity> list){
        return list.stream()
                .map(ent -> new DistrictReport(ent))
                .collect(Collectors.toList());
    }


    public List getDistrictSingleRegistered() {
        return getDistrictsList().stream()
                .filter(dr -> dr.isResultSingleRegistered())
                .filter(dr -> !dr.isResultSingleApproved())
                .collect(Collectors.toList());
    }

    public List getDistrictMultiRegistered() {
        return getDistrictsList().stream()
                .filter(dr -> dr.isResultMultiRegistered())
                .filter(dr -> !dr.isResultMultiApproved())
                .collect(Collectors.toList());

    }

    public String getNameById(Long id) {
        return repository.findById(id).getName();
    }

    public List getDistictsByPage(Long id) {
        List<DistrictEntity> content = pageRepository.findAll(new PageRequest(id.intValue(), 20)).getContent();
        return content.stream().map(d -> new DistrictReport(d)).collect(Collectors.toList());
    }
}
