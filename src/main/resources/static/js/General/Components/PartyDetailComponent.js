function getPartyMembersList(self) {
    var partyMembersList = [];
    self.props.partyDetails.members.map(function(member,index) {
      partyMembersList.push(
        <PartyDetailRowViewComponent
          id={member.id}
          key={index}
          name={member.name}
          surname={member.surname}
          birthDate={member.birthDate}
          description={member.description}
          numberInParty={member.numberInParty}
          countyName={member.countyName}
        />
      );
  });
    return partyMembersList;
}
function getRatedPartyMembersList(self) {
  if (self.props.generalResults.votesInMulti != undefined) {
    var vip = self.props.generalResults.votesInMulti;
    /*
    for (var i = 0; i < party.members.length; i++) {
      console.log(party.members[i]);
      party.members[i]
    } */
        var ratedMembersList =[];
      vip.forEach(function(obj){
        if(obj.par.id == self.props.pid){
        obj.par.members.forEach(function(member,index) {
            ratedMembersList.push(
              <PartyDetailRowViewComponent
              id={member.id}
              key={index}
              name={member.name}
              surname={member.surname}
              birthDate={member.birthDate}
              description={member.description}
              numberInParty={member.numberInParty}
              countyName={member.countyName}
              />
            );
          });

          }
        });
    return ratedMembersList;
  }
}
function getSingleWinnersByPartyList(self) {
  if (self.props.generalResults.singleWinners != undefined) {
        var singleWinnersByPartyList =[];
      self.props.generalResults.singleWinners.forEach(function(obj){
        var singleWinners = [];
        if(obj.candidate.partijosId == self.props.pid){
          singleWinners.push(obj.candidate)
        singleWinners.forEach(function(member,index) {
            singleWinnersByPartyList.push(
              <PartyDetailRowViewComponent
              id={member.id}
              key={member.id+'2652652'}
              name={member.name}
              surname={member.surname}
              birthDate={member.birthDate}
              description={member.description}
              numberInParty={member.numberInParty}
              countyName={member.countyName}
              />
            );
          });
        }
        });
    return singleWinnersByPartyList;
  }
}
function getpartyWinnersMembersList(self, numberOfWinners) {
  if (self.props.generalResults.votesInMulti != undefined) {
    var vip = self.props.generalResults.votesInMulti;
          var partyWinnersMembersList =[];
        vip.forEach(function(obj){
          if(obj.par.id == self.props.pid){
            var  partyMebersList = [];
            for(var i = 0; i < numberOfWinners; i++){
              partyMebersList.push(
                obj.par.members[i]
              );
            }
            partyMebersList.map(function(member,index) {
                partyWinnersMembersList.push(
                  <PartyDetailRowViewComponent
                  id={member.id}
                  key={index}
                  name={member.name}
                  surname={member.surname}
                  birthDate={member.birthDate}
                  description={member.description}
                  numberInParty={member.numberInParty}
                  countyName={member.countyName}
                  />
                );
            });
          }
        });
      return partyWinnersMembersList;
  }
}
function getMandatesPerParty(self) {
  if (self.props.generalResults.mandatesPerParty != undefined) {
        var mandatesPerParty = null;
      self.props.generalResults.mandatesPerParty.forEach(function(obj){
        if(obj.par.id == self.props.pid){
          console.log("mandatesPerParty");
          console.log(obj.count);
          mandatesPerParty= obj.count;
        }
        });
    return mandatesPerParty;
  }
}
var PartyDetailComponent = React.createClass({
  render: function() {
    var mandatesPerParty = getMandatesPerParty(this);
    var partyMembersList = getPartyMembersList(this);
    var ratedMembersList = getRatedPartyMembersList(this);
    var partyWinnersMembersList = getpartyWinnersMembersList(this, mandatesPerParty-1);
    var singleWinnersByPartyList = getSingleWinnersByPartyList(this);
    return (
      <div className="col-md-12">
        <div className="container"><h1 className='yellow'>Partija: {this.props.partyDetails.name}</h1>
        </div>
            {/* nav-pills  */}
            <ul  className="nav nav-pills secondmenu">
              <li className="active">
                <a  href="#1a" data-toggle="tab">Partijos Informacija</a>
              </li>
              <li>
                <a  href="#2a" data-toggle="tab">Pirminis Partijos narių sąrašas</a>
              </li>
              <li>
                <a  href="#3a" data-toggle="tab">Sureitinguotas Partijos narių sąrašas</a>
              </li>
            </ul>
            <div id="exTab1" className="container shadow">
              <div className="tab-content clearfix">
                {/* General info tab-1 */}
                <div className="tab-pane active" id="1a">
                  <table className="table table-striped">
                    <thead>
                      <tr>
                        <th className='col-md-12 col-sm-12'>Partijos sąrašo numeris: {this.props.partyDetails.partyNumber}</th>
                      </tr>
                    </thead>
                    <tbody>
                        <th className='col-md-12 col-sm-12'>Reikia lentelės, kurioje matytusi pirmieji partijos sąrašo nariai,
                        vėliau pagal rezultatus bus galima padaryti, kad rodytų patenkančius į seimą.</th>
                    </tbody>
                  </table>
                    <span className='col-md-12 col-sm-12'>Partijos narių sąrašas daugiamandatėje patenkantis į seimą, pagal momentinius rezultatus. Tai ne galutinis sąrašas, kol rinkimai vis dar vyksta</span>
                  <table className="table table-striped">
                    <thead>
                      <tr>
                        <th className='col-md-1 col-sm-1'>Nr.</th>
                        <th className='col-md-2 col-sm-2'>Vardas</th>
                        <th className='col-md-3 col-sm-3'>Pavardė</th>
                        <th className='col-md-2 col-sm-2'>Gimimo data</th>
                        <th className='col-md-2 col-sm-1'>Aprašymas</th>
                        <th className='col-md-2 col-sm-2'>Apygarda</th>
                      </tr>
                    </thead>
                    <tbody>
                          {partyWinnersMembersList}
                    </tbody>
                  </table>
                    <span className='col-md-12 col-sm-12'>Partijos narių sąrašas vienmandatėje patenkantis į seimą, pagal momentinius rezultatus. Tai ne galutinis sąrašas, kol rinkimai vis dar vyksta</span>
                  <table className="table table-striped">
                    <thead>
                      <tr>
                        <th className='col-md-1 col-sm-1'>Nr.</th>
                        <th className='col-md-2 col-sm-2'>Vardas</th>
                        <th className='col-md-3 col-sm-3'>Pavardė</th>
                        <th className='col-md-2 col-sm-2'>Gimimo data</th>
                        <th className='col-md-2 col-sm-1'>Aprašymas</th>
                        <th className='col-md-2 col-sm-2'>Apygarda</th>
                      </tr>
                    </thead>
                    <tbody>
                      {singleWinnersByPartyList}
                    </tbody>
                  </table>
                </div>
                {/* Party members list tab-2 */}
                <div className="tab-pane" id="2a">
                  <h3>Partijos narių sąrašas prieš rinkimus</h3>
                  <div className='col-md-12'>
                    <table className="table table-striped">
                      <thead>
                        <tr>
                          <th className='col-md-1 col-sm-1'>Nr.</th>
                          <th className='col-md-2 col-sm-2'>Vardas</th>
                          <th className='col-md-3 col-sm-3'>Pavardė</th>
                          <th className='col-md-2 col-sm-2'>Gimimo data</th>
                          <th className='col-md-2 col-sm-1'>Aprašymas</th>
                          <th className='col-md-2 col-sm-2'>Apygarda</th>
                        </tr>
                      </thead>
                      <tbody>
                        {partyMembersList}
                      </tbody>
                    </table>
                  </div>
                </div>
                {/* Single candidates list tab-3 */}
                <div className="tab-pane" id="3a">
                  <h3>Partijos narių sąrašas pagal gautus reitingų rezultatus</h3>
                  <div className='col-md-12'>
                    <table className="table table-striped">
                      <thead>
                        <tr>
                          <th className='col-md-1 col-sm-1'>Nr.</th>
                          <th className='col-md-5 col-sm-3'>Vardas</th>
                          <th className='col-md-2 col-sm-2'>Partija</th>
                          <th className='col-md-2 col-sm-2'>Gimimo data</th>
                          <th className='col-md-2 col-sm-2'>Aprašymas</th>
                          <th className='col-md-2 col-sm-2'>Apygarda</th>
                        </tr>
                      </thead>
                      <tbody>
                        {ratedMembersList}
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
      </div>
    );
  },
});
window.PartyDetailComponent = PartyDetailComponent;
