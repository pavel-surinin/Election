var PartyDetailViewComponent = React.createClass({
  render: function() {
      var array = [];
      this.props.partyDetails.members.map(function(member,index) {
        array.push(
          <PartyDetailListRowViewComponent
            id={member.id}
            key={index}
            name={member.name}
            surname={member.surname}
            birthDate={member.birthDate}
            description={member.description}
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
		          <div className="panel-heading"><h2>{this.props.partyDetails.name}</h2></div>
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
window.PartyDetailViewComponent = PartyDetailViewComponent;
