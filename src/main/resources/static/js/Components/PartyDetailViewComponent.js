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
<<<<<<< HEAD
      <div className="panel panel-default">
          <div className="panel-heading"><h2>{this.props.partyDetails.name}</h2>
            <button onClick={this.props.onHandleDeleteClick} className="btn btn-danger btn-sm fa fa-trash"></button>
          </div>
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
=======

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

>>>>>>> 33d72678e7d3758dbf8d8a5ed27014994b4f68c8
    );
  }
});
window.PartyDetailViewComponent = PartyDetailViewComponent;
