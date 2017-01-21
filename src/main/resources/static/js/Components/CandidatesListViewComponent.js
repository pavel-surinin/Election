var CandidatesListViewComponent = React.createClass({

	render: function() {
	    var array = [];
	    this.props.candidateList.map(function(cand,index) {
	      array.push(
	        <CandidatesListViewRowComponent
	          id={cand.id}
	          key={index}
	          name={cand.name}
	          surname={cand.surname}
	          birthDate={cand.birthDate}
	          partijosId={cand.partijosId}
	          partijosPavadinimas={cand.partijosPavadinimas}
	          description={cand.description}
	        />
	      );
	    });
	    
    return (
    	  <div className="container-fluid">
		    <div className="row">
			  <div className="col-xs-12 col-sm-3 col-md-3 col-lg-3 sidenav">
				<SideNavBarComponent />
			  </div>
			  <div className="col-xs-12 col-sm-9 col-md-9 col-lg-9 text-left">
    		    <div className="panel panel-default">
		          <div className="panel-heading"><h2>Kandidatų sąrašas</h2></div>
		            <table className="table table-striped">
		             <thead>
		              <tr>
			              <th>
			                Eil. Nr.
			              </th>
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
			                Partinė priklausomybė
			              </th>
			              <th>
			                Informacija
			              </th>
			              <th>
			                Veiksmai
			              </th>
		              </tr>
		            </thead>
		            <tbody>
					  {array}
		            </tbody>
		          </table>
		        </div>
		      </div>
		    </div>
		  </div>
    );
  }
});

window.CandidatesListViewComponent = CandidatesListViewComponent;