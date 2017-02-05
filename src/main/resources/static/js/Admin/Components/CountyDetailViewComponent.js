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
		  this.props.countyDetails.candidates.map(function(candidate,index) {
			  cand.push(
					<li className="list-group-item text-primary" key={index}>{candidate.name} {candidate.surname} - {candidate.partijosPavadinimas}</li>
		    );
		  });
    console.log(this.props.countyDetails.candidates);
		return (
						<div className="panel panel-default">
				          <div className="panel-heading" style={{paddingTop:20,paddingBottom:20}}>
				            <h3>
											{this.props.countyDetails.name} rinkimų apygarda
										</h3>
				          </div>
									<div className="panel-body">
										<button type="button" id="candidate-button" className="btn btn-outline btn-primary btn-sm" data-toggle="collapse" data-parent="#accordion" href="#collapse2">Kandidatai</button>
										 &nbsp;
										<button data-toggle="collapse" id="district-button" type="button" data-parent="#accordion" className="btn btn-outline btn-primary btn-sm" href="#collapse1">Apylinkės</button>
									</div>
					      <div className="panel-group" id="accordion">
					          <div className="panel-heading">
					            <h4 className="panel-title">
					            </h4>
					          </div>
					          <div id="collapse1" className="panel-collapse collapse in">
								        <table className="table table-striped table-bordered table-hover">
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

					          <div className="panel panel-default">
					            <div className="panel-heading">
					              <h4 className="panel-title">
					              </h4>
					            </div>
					            <div id="collapse2" className="panel-collapse collapse panel-body">
												<ul className="list-group">
												{cand}
												</ul>
					              </div>
					            </div>
					          </div>
					        </div>
	    );
	  }
	});

	window.CountyDetailViewComponent = CountyDetailViewComponent;
