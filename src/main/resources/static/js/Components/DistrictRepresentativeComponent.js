var gs = window.generalStyles;

var DistrictRepresentativeComponent = React.createClass({
  render: function() {
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
      
        <div className="container-fluid">
		  <div className="row">
			<div className="col-xs-12 col-sm-3 col-md-3 col-lg-3 sidenav">
				<SideNavBarComponent />
		    </div>
		    <div className="col-xs-12 col-sm-9 col-md-9 col-lg-9 text-left">
		      <div style={gs.shopperStyle} className="panel panel-default">
		        <div className="panel-heading">
		          <h3>Apylinkės Atstovų sarašas</h3>
		        </div>
		          <div className="panel-body">
		            <a href="#/admin/representative/create"><button type="button" className="btn btn-success btn-sm">Registruoti</button></a>
		            <span>
		              <h5>Registruoti naują apylinkės atstovą </h5>
		            </span>
		          </div>
		            <table className="table table-striped">
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
		                  <th>
		                    Redaguoti
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
