var PartyComponent = React.createClass({
  render: function() {
    var partyMembersList = [];
    this.props.partyDetails.members.map(function(member,index) {
      partyMembersList.push(
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
      <div>
        <div className="container"><h1>Partija: {this.props.partyDetails.name}</h1></div>
          <div id="exTab1" className="container">
            {/* nav-pills  */}
            <ul  className="nav nav-pills">
              <li className="active">
                <a  href="#1a" data-toggle="tab">Partijos Informacija</a>
              </li>
              <li>
                <a  href="#2a" data-toggle="tab">Partijos narių sąrašas</a>
              </li>
              <li>
                <a  href="#3a" data-toggle="tab">Partijos nariai dalyvaujantys vienmandatėje</a>
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
                          <th className='col-md-2 col-sm-2'>Vardas</th>
                          <th className='col-md-3 col-sm-3'>Pavardė</th>
                          <th className='col-md-2 col-sm-2'>Gimimo data</th>
                          <th className='col-md-2 col-sm-1'>Aprašymas</th>
                        </tr>
                      </thead>
                      <tbody>
                        {partyMembersList}
                      </tbody>
                    </table>
                  </div>
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

                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
          </div>
      </div>
    );
  }
});

window.PartyComponent = PartyComponent;
