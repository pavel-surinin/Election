var PartyListViewComponent = React.createClass({
  render: function() {
    var array = [];
    this.props.partyList.map(function(party,index) {
      array.push(
        <PartyListViewRowComponent
          id={party.id}
          key={index}
          name={party.name}
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
		          <h3>Partijų sąrašas</h3>
		        </div>
		          <div className="panel-body">
		            <a href="#/admin/party/create"><button type="button" className="btn btn-success btn-sm">Registruoti</button></a>
		            <span>
		              <h5>Registruoti naują partiją </h5>
		            </span>
		          </div>
		            <table className="table table-striped">
		              <thead>
		                <tr>
		                  <th>
		                    Nr
		                  </th>
		                  <th>
		                    Partija
		                  </th>
		                  <th>
		                    Kandidatų sąrašas
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

window.PartyListViewComponent = PartyListViewComponent;
