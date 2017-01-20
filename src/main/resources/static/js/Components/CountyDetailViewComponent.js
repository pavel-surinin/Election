var CountyDetailViewComponent = React.createClass({
	  
	render: function() {
		return (
			<div className="panel panel-default">
		        <div className="panel-heading">
		          <h3>Rinkimų apygardų sąrašas</h3>
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
