function getPartyGeneralInfo(self) {
  var dis = self.props.party;
  var partyInfo = [];
  if(dis.partyNumber) {partyInfo.push(<tr><td>{dis.partyNumber} Partija</td></tr>);}
  if (dis.name) { partyInfo.push(<tr><td>{dis.name} Partija</td></tr>);}
  if(dis.members) { partyInfo.push(<tr><td>Partijos narių skaičius: {dis.members.size()} </td></tr>);}
  return partyInfo;
}
function getPartyMembersTable(self){
  var membersTable = [];
    membersTable = self.props.party.members.map(function(tai,index){
      return (
        <tr key={index} className='small'>
          <td>{tai.members.numberInParty}</td>
          <td>{tai.members.name}</td>
          <td>{tai.members.surname}</td>
          <td>{tai.members.birthDate}</td>
          <td>  <div className="modal-dialog">
                  <div className="modal-content">
                    <div className="modal-header">
                      <button type="button" id="modal-close-button" className="close" data-dismiss="modal">&times;</button>
                      <h4 className="modal-title">{this.props.name} {this.props.surname}</h4>
                    </div>
                    <div className="modal-body">
                      {this.props.description}
                    </div>
                    <div className="modal-footer">
                      <button type="button" id="close-button"  className="btn btn-default" data-dismiss="modal">Uždaryti</button>
                    </div>
                  </div>
                </div>
            </td>
        </tr>
      );
    });
  return singleTable;
}

var PartyViewComponent  = React.createClass({
  render: function() {
    return (
      <div>
        <div className="container"><h1>{this.props.party.name} partija</h1></div>
          <div id="exTab1" className="container">
            {/* nav-pills  */}
            <ul  className="nav nav-pills">
              <li className="active">
                <a  href="#1a" data-toggle="tab">Partijos Informacija</a>
              </li>
              <li style={singleStyle}>
                <a onClick={this.loadPartyMembersList} href="#2a" data-toggle="tab">Partijos narių sąrašas</a>
              </li>
              <li style={multiStyle}>
                <a onClick={this.loadPartySingleCandidatesList} href="#3a" data-toggle="tab">Partijos nariai dalyvaujantys vienmandatėje</a>
              </li>
            </ul>
              <div className="tab-content clearfix">
                {/* General info tab-1 */}
                <div className="tab-pane active" id="1a">
                  <table className="table table-striped">
                    <thead>
                      <tr>
                        <th className='col-md-10 col-sm-10'>Bendra Informacija</th>
                      </tr>
                    </thead>
                    <tbody>
                          Reikia lentelės, kurioje matytusi pirmieji partijos sąrašo nariai,
                          vėliau pagal rezultatus bus galima padaryti, kad rodytų patenkančius į seimą.
                          {partyInfo}
                    </tbody>
                  </table>
                </div>
                {/* Party members list tab-2 */}
                <div className="tab-pane vytis" id="2a">
                  <h3>Narių sąrašas</h3>
                  <div className='col-md-7'>
                  <table className="table table-striped">
                    <thead>
                      <tr>
                        <th className='col-md-1 col-sm-1'>Nr.</th>
                        <th className='col-md-3 col-sm-3'>Vardas</th>
                        <th className='col-md-2 col-sm-2'>Pavardė</th>
                        <th className='col-md-2 col-sm-2'>Gimimo data</th>
                        <th className='col-md-2 col-sm-1'>Aprašymas</th>
                      </tr>
                    </thead>
                    <tbody>
                      {singleTable}
                    </tbody>
                  </table>
                </div>
                {/* Single candidates list tab-3 */}
                <div className="tab-pane vytis" id="3a">
                  <h3>Partijos narių sąrašas dalyvaujančių vienmandatėse rinkimų apygardose</h3>
                  <div className='col-md-7'>
                    <table className="table table-striped">
                      <thead>
                        <tr>
                          <th className='col-md-1 col-sm-1'>Nr.</th>
                          <th className='col-md-5 col-sm-3'>Vardas</th>
                          <th className='col-md-2 col-sm-2'>Partija</th>
                          <th className='col-md-2 col-sm-2'>Gimimo data</th>
                          <th className='col-md-2 col-sm-2'>Aprašymas</th>
                          <th className='col-md-2 col-sm-2'>Apygarda.......</th>
                        </tr>
                      </thead>
                      <tbody>
                        {singleCandidatesTable}
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
          </div>
        </div>
      </div>
    );
  },
});

window.PartyViewComponent = PartyViewComponent;
