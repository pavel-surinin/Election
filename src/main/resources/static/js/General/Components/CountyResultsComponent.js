function getSingleTable(self) {
    var singleTable = [];
    if (self.props.results.votesByCandidate) {
        singleTable = self.props.results.votesByCandidate.map(function (cid, index) {
            return (
                <tr key={index} className='small'>
                    <td>{cid.candidate.name + ' ' + cid.candidate.surname}</td>
                    <td>{cid.candidate.partijosPavadinimas}</td>
                    <td>{cid.votes}</td>
                    <td>{Math.round(cid.votes/self.props.results.validCount*100)}%</td>
                    <td>{Math.round(cid.votes/self.props.results.votersCount*100)}%</td>
                </tr>
            );
        });
    }
    return singleTable;
}

function getMultiTable(self) {
    if (self.props.results.votesByParty) {
        var multiTable = self.props.results.votesByParty.map(function(p,index){
            var rating = <CountyResultsMultiTableRow info={p} />;
            if (self.props.results.votesByParty) {
                return(
                    <tr key={index}>
                        <td>{p.par.partyNumber}</td>
                        <td>{p.par.name}</td>
                        <td>{rating}</td>
                        <td>{p.votes}</td>
                        <td>{Math.round(p.votes/self.props.results.validCount*100)}%</td>
                        <td>{Math.round(p.votes/self.props.results.votersCount*100)}%</td>
                    </tr>);
            }});

        return multiTable;
    }
}

function prepareCanvas(chartType,electionType){
    var sp1 = document.createElement('canvas');
    sp1.id = 'canvas' + chartType + electionType;
    var parentDiv = document.getElementById('parent' + chartType + electionType);
    parentDiv.removeChild(document.getElementById('canvas' + chartType + electionType));
    parentDiv.appendChild(sp1);
}

var CountyResultsComponent = React.createClass({
    loadSingle : function() {
        prepareCanvas('Pie','Single');
        prepareCanvas('Bar','Single');
        chartss.pie(document.getElementById('canvasPieSingle'), this.props.results, 'Single');
        chartss.bar('horizontalBar',document.getElementById('canvasBarSingle'), this.props.results,'Single');
    },

    loadMulti : function() {
        prepareCanvas('Pie','MultiCounty');
        prepareCanvas('Bar','MultiCounty');
        chartss.pie(document.getElementById('canvasPieMultiCounty'), this.props.results, 'MultiCounty');
        chartss.bar('bar',document.getElementById('canvasBarMultiCounty'), this.props.results, 'MultiCounty');
    },

    render: function(){
        var singleTable = getSingleTable(this);
        var multiTable = getMultiTable(this);
        var self = this;
        var districts = this.props.county.districts.map(function(district,index){
            return(
              <tr>
                <td className="text-info"><a href={"#/county/" + self.props.countyId + "/" + district.id}>
                  <i className="fa fa-angle-right" aria-hidden="true"></i> {district.name}</a></td>
                <td className="text-info"><i className="fa fa-address-book "
                                             aria-hidden="true"></i> {district.representativeName}</td>
                <td className="text-info"><i className="fa fa-map-o" aria-hidden="true"></i> {district.adress}</td>
                <td className="text-info"><i className="fa fa-users" aria-hidden="true"></i> {district.numberOfElectors}</td>
              </tr>

            );
        });
        var county = this.props.county;
        var countyResults = this.props.results;
        var countyInfo = [];
        if (county.name) { countyInfo.push(<tr><td>{county.name} apygarda</td></tr>);}
        if (countyResults.districtsCount){countyInfo.push(<tr><td>Apylinkių skaičius: {countyResults.districtsCount}</td></tr>)}
        if (countyResults.districtsVotedSingle){countyInfo.push(<tr><td>Vienmandatėje prabalsavo apylinkių: {countyResults.districtsVotedSingle} </td></tr>)}
        if (countyResults.singleMandateWinner){countyInfo.push(<tr><td>Vienmandatės nugalėtojas: {countyResults.singleMandateWinner.name} {countyResults.singleMandateWinner.surname}</td></tr>)}
        if (countyResults.districtsVotedMulti){countyInfo.push(<tr><td>Daugiamandatėje prabalsavo apylinkių: {countyResults.districtsVotedMulti} </td></tr>)}
        if (countyResults){countyInfo.push(<tr><td><ResultsApiComponent territory='county' id={this.props.county.id}/></td></tr>)}

        return (
            <div className="col-md-12">
                <div className="container">
                  <h1 className='yellow'>{this.props.county.name} rinkimų apygarda</h1></div>
                  <ul  className="nav nav-pills secondmenu">
                  <li className="active">
                  <a  href="#1a" data-toggle="tab">Apygardos Informacija</a>
                  </li>
                  <li>
                  <a href="#2a" onClick={this.loadSingle} data-toggle="tab">Vienmandatės rezultatai</a>
                  </li>
                  <li>
                  <a href="#3a" onClick={this.loadMulti} data-toggle="tab">Daugiamandatės rezultaitai</a>
                  </li>
                  <li>
                  <a href="#4a" data-toggle="tab">{this.props.county.name} apylinkės</a>
                  </li>
                  </ul>
                <div id="exTab1" className="container shadow">
                    {/* General info here */}
                    <div className="tab-content clearfix">
                        <div className="tab-pane active" id="1a">
                            <table className="table table-striped">
                                <thead>
                                <tr>
                                    <th className='col-md-10 col-sm-10'>Bendra Informacija</th>
                                </tr>
                                </thead>
                                <tbody>
                                {countyInfo}
                                </tbody>
                            </table>
                        </div>
                        {/*Single graphs here*/}
                        <div className="tab-pane vytis" id="2a">
                          <h3>Rinkėjų aktyvumas: {this.props.results.validCount} balsavusių iš {this.props.results.votersCount}, {Math.round(this.props.results.validCount / this.props.results.votersCount *100,2)}%</h3>
                          <h3>Sugadintų biuletenių skaičius vienmandatėje: {this.props.results.spoiledSingle}</h3>
                            <div className='col-md-7'>
                                <table className="table table-striped">
                                    <thead>
                                    <tr>
                                        <th className='col-md-4 col-sm-4'>Kandidatas</th>
                                        <th className='col-md-3 col-sm-3'>Partija</th>
                                        <th className='col-md-2 col-sm-2'>Balsai</th>
                                        <th className='col-md-2 col-sm-2'>% nuo galiojančių</th>
                                        <th className='col-md-1 col-sm-2'>% nuo visų</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {singleTable}
                                    </tbody>
                                </table>
                                {/*chart pie*/}
                            </div>
                            <div className='chartContainer col-md-5' id='parentPieSingle'>
                                <canvas id='canvasPieSingle'></canvas>
                            </div>
                            {/*chart bar*/}
                            <div className='chartContainer col-md-12' id='parentBarSingle'>
                                <canvas id='canvasBarSingle'></canvas>
                            </div>

                        </div>

                            <div className="tab-pane vytis" id="3a">
                              <h3>Rinkėjų aktyvumas: {this.props.results.validCount} balsavusių iš {this.props.results.votersCount}, {Math.round(this.props.results.validCount / this.props.results.votersCount *100,2)}%</h3>
                              <h3>Sugadintų biuletenių skaičius daugiamandatėje: {this.props.results.spoiledMulti}</h3>
                                <div className='col-md-7'>
                                    <table className="table table-striped">
                                        <thead>
                                        <tr>
                                            <th className='col-md-1 col-sm-1'>Nr.</th>
                                            <th className='col-md-5 col-sm-3'>Partija</th>
                                            <th className='col-md-2 col-sm-2'>Retingai</th>
                                            <th className='col-md-2 col-sm-2'>Balsai</th>
                                            <th className='col-md-2 col-sm-2'>% nuo galiojančių</th>
                                            <th className='col-md-2 col-sm-2'>% nuo visų</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        {multiTable}
                                        </tbody>
                                    </table>
                                </div>

                                <div className='chartContainer col-md-5' id='parentPieMultiCounty'>
                                    <canvas id='canvasPieMultiCounty'></canvas>
                                </div>
                                {/*chart bar*/}
                                <div className='chartContainer col-md-12' id='parentBarMultiCounty'>
                                    <canvas id='canvasBarMultiCounty'></canvas>
                                </div>
                            </div>
                        <div className="tab-pane" id="4a">
                            <h3>{this.props.county.name} apygardos apylinkės</h3>
                            <table className="table table-striped">
                                <thead>
                                <tr>
                                    <th className='col-md-4 col-sm-4'>Pavadinimas</th>
                                    <th className='col-md-3 col-sm-3'>Atstovas</th>
                                    <th className='col-md-4 col-sm-3'>Adresas</th>
                                    <th className='col-md-2 col-sm-2'>Rinkėjų skaičius</th>

                                </tr>
                                </thead>
                                <tbody>
                                {districts}
                                </tbody>
                            </table>

                        </div>
                    </div>
                </div>
            </div>

        );
    },
});
window.CountyResultsComponent = CountyResultsComponent;
