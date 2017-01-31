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
            numberInParty={member.numberInParty}
          />
        );
      });

    return (
      <div className="panel panel-default">
          <div className="panel-heading"><h2>{this.props.partyDetails.name}</h2>
            <a id="delete-button" className="btn btn-danger" onClick={this.props.onHandleDeleteClick}>  <i className="fa fa-trash-o fa-lg"></i> Ištrinti sąrašą</a>
            <a id="back-button" className="btn btn-success" href="#/admin/party" role="button">Atgal</a>
          </div>
          <div className="panel-body">
            <table className="table table-striped table-bordered table-hover">
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
    );
  }
});
window.PartyDetailViewComponent = PartyDetailViewComponent;
