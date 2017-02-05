var DistrictRepresentativeComponent = React.createClass({
  render: function() {
    var succesCreateMessage = alerts.showSucces(this.props.succesCreateText);
    var credentialsMessage = alerts.showInfo(this.props.credentials);
    var array = [];
    this.props.representativesList.map(function(rep,index) {
      array.push(
        <DistrictRespesentativeListViewRowComponent
          id={rep.id}
          key={index}
          name={rep.name}
          surname={rep.surname}
          districtId={rep.districtId}
          districtName={rep.districtName}
        />
      );
    });
    return (

		      <div className="panel panel-default">
		        <div className="panel-heading" style={{paddingTop:20,paddingBottom:20}}>
		          <h4 style={{display:'inline'}}><i className="fa fa-table"></i>&nbsp; Apylinkės Atstovų sarašas</h4>
              <div className="text-success pull-right">
                <a href="#/admin/representative/create"  id="register-button" className="text-success"><i className="fa fa-plus"></i>
                &nbsp; Registruoti
                </a>
              </div>
		        </div>
		          <div className="panel-body">
<<<<<<< HEAD:src/main/resources/static/js/Components/DistrictRepresentativeComponent.js
		            <a href="#/admin/representative/create">
		              <button type="button" className="btn btn-success btn-sm">Registruoti naują apylinkės atstovą</button>
		            </a>
=======
                {succesCreateMessage}
                {credentialsMessage}
>>>>>>> pavel-surinin-master:src/main/resources/static/js/Admin/Components/DistrictRepresentativeComponent.js
		          </div>
		            <table className="table table-striped table-hover">
		              <thead>
		                <tr>
		                  <th>
		                    Nr
		                  </th>
		                  <th>
		                    Vardas
		                  </th>
		                  <th>
		                    Pavarde
		                  </th>
		                  <th>
		                    Atstovaujama Apylinkė
		                  </th>
		                </tr>
		              </thead>
		              <tbody>
		              {array}
		              </tbody>
		            </table>
              </div>
    );
  }
});

window.DistrictRepresentativeComponent = DistrictRepresentativeComponent;
