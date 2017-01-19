var CountyDetailViewComponent = React.createClass({
	  
	render: function() {
		return (
			<div className="panel panel-default">
		        <div className="panel-heading">
		          <h3>Rinkimų apygardų sąrašas</h3>
		        </div>
		        <div id="accordion" role="tablist" aria-multiselectable="true">
		        <div className="card">
		          <div className="card-header" role="tab" id="headingOne">
		            <h5 className="mb-0">
		              <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
		                Kandidatai
		              </a>
		            </h5>
		          </div>

		          <div id="collapseOne" className="collapse show" role="tabpanel" aria-labelledby="headingOne">
		            <div className="card-block">
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

		              </tbody>
		            </table>
		            </div>
		          </div>
		        </div>
		        <div className="card">
		          <div className="card-header" role="tab" id="headingTwo">
		            <h5 className="mb-0">
		              <a className="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
		                Apylinkės
		              </a>
		            </h5>
		          </div>
		          <div id="collapseTwo" className="collapse" role="tabpanel" aria-labelledby="headingTwo">
		            <div className="card-block">
			            <table className="table table-striped">
			              <thead>
			                <tr>
			                  <th>
			                    Apylinkė
			                  </th>
			                  <th>
			                    Atstovas apylinkėje
			                  </th>
			                </tr>
			              </thead>
			              <tbody>
	
			              </tbody>
			            </table>
		            </div>
		          </div>
		        </div>
		      </div>
			</div>
	    );
	  }
	});

	window.CountyDetailViewComponent = CountyDetailViewComponent;
