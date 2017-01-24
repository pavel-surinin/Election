var CountyDetailViewComponent = React.createClass({
	render: function() {
	      var dist = [];
	      this.props.countyDetails.districts.map(function(districts,index) {
	    	dist.push(
	        <CountyDetailViewDistrictRowComponent
	          id={districts.id}
	          key={index}
	          name={districts.name}
	          representativeId={districts.representativeId}
	          representativeName={districts.representativeName}
	        />
	      );
	    });
		
		  var cand = [];
		  this.props.countyDetails.candidates.map(function(candidates,index) {
			  cand.push(
		      <CountyDetailViewCandidatesRowComponent
		          id={candidates.id}
		          key={index}
		          name={candidates.name}
		          surname={candidates.surname}
		          birthDate={candidates.birthDate}
		          partijosId={candidates.partijosId}
		          partijosPavadinimas={candidates.partijosPavadinimas}
		      />
		    );
		  });
    
		return (
						<div className="panel panel-default">
				          <div className="panel-heading">
				            <h3>{this.props.countyDetails.name} rinkimų apygarda</h3>
				          </div>
					      <div className="panel-group" id="accordion">
					        <div className="panel panel-default">
					          <div className="panel-heading">
					            <h4 className="panel-title">
					              <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">Apylinkės</a>
					            </h4>
					          </div>
					          <div id="collapse1" className="panel-collapse collapse in">
						        <table className="table table-striped">
						          <thead>
						            <tr>
						              <th>
						                Pavadinimas
						              </th>
						              <th>
						                Atstovas
						              </th>
						              </tr>
						            </thead>
						            <tbody>
						              {dist}
						            </tbody>
						          </table>
					            </div>
					          </div>
	
					          <div className="panel panel-default">
					            <div className="panel-heading">
					              <h4 className="panel-title">
					                <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">Kandidatai</a>
					              </h4>
					            </div>
					            <div id="collapse2" className="panel-collapse collapse">
						            <table className="table table-striped">
						              <thead>
						                <tr>
						                  <th>
						                    Vardas
						                  </th>
						                  <th>
						                    Pavardė
						                  </th>
						                  <th>
						                    Gimimo data
						                  </th>
						                  <th>
						                    Partija
						                  </th>
						                </tr>
						              </thead>
						              <tbody>
						              	{cand}
						              </tbody>
						            </table>
					              </div>
					            </div>
					          </div>
					        </div>
	    );
	  }
	});

	window.CountyDetailViewComponent = CountyDetailViewComponent;
