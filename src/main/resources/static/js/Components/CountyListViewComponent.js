
var CountyListViewComponent = React.createClass({
  render: function() {
    var array = [];
    this.props.countyList.map(function(county,index) {
      array.push(
        <CountyListRowViewComponent
          id={county.id}
          key={index}
          name={county.name}
          districts={county.districts}
          candidates={county.candidates}
          parties={county.parties}
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
		        <div className="panel-heading">
		          <h3>Rinkimų apygardų sąrašas
		
		          </h3>
		        </div>
		          <div className="panel-body">
		            <h5>
		              <a href="#/admin/county/create">
		                <button type="button" className="btn btn-success btn-sm">
		                  Registruoti naują Apygarda
		                </button>
		              </a>
		            </h5>
		          </div>
		            <table className="table table-striped">
		              <thead>
		                <tr>
		                  <th>
		                    Nr
		                  </th>
		                  <th>
		                    Apygarda
		                  </th>
		                  <th>
		                    Detali informacija
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

window.CountyListViewComponent = CountyListViewComponent;
