/**
 * Created by nevyt on 2/21/2017.
 */
var CountyResultsComponent = React.createClass({
    render: function(){
        console.log('this from countyresults:',this);
        var self = this;
        var districts = this.props.county.districts.map(function(district,index){
            return(
                <tr>
                    <td><a href={"#/counties/" + self.props.countyId + "/" + district.id}>{district.name}</a></td>
                    <td>{district.adress}</td>
                    <td>{district.numberOfElectors}</td>
                </tr>

            );
        });
        var county = this.props.county;
        var countyResults = this.props.results;
        var countyInfo = [];
        if (county.name) { countyInfo.push(<tr><td>{county.name} apygarda</td></tr>);}
        if (countyResults.districtsCount){countyInfo.push(<tr><td>Apylinkių skaičius: {countyResults.districtsCount}</td></tr>)}
        if(countyResults.singleMandateWinner){countyInfo.push(<tr><td>Vienmandatės nugalėtojas: {countyResults.singleMandateWinner.name} {countyResults.singleMandateWinner.surname}</td></tr>)}

        return (
            <div className="col-md-12">
                <div className="container"><h1>{this.props.county.name} rinkimų apygarda</h1></div>
                <div id="exTab1" className="container">
                    <ul  className="nav nav-pills">
                        <li className="active">
                            <a  href="#1a" data-toggle="tab">Apygardos Informacija</a>
                        </li>
                        <li>
                            <a href="#2a" data-toggle="tab">Vienmandatės rezultatai</a>
                        </li>
                        <li>
                            <a href="#3a" data-toggle="tab">Daugiamandatės rezultaitai</a>
                        </li>
                        <li>
                            <a href="#4a" data-toggle="tab">{this.props.county.name} apylinkės</a>
                        </li>
                    </ul>

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

                        <div className="tab-pane vytis" id="2a">
                            <h3>Super vienmandates rezultatai ir grafikeliai [][][][][][][--=-=0-=0[][]]</h3>
                        </div>

                        <div className="tab-pane" id="3a">
                            <h3>Super daugiamandates rezultatai ir grafikeliai [][][][][][][--=-=0-=0[][]]</h3>
                        </div>
                        <div className="tab-pane" id="4a">
                            <h3>Sarasiukas districtu</h3>
                            <table className="table table-striped">
                                <thead>
                                <tr>
                                    <th className='col-md-4 col-sm-5'>Pavadinimas</th>
                                    <th className='col-md-4 col-sm-5'>Adresas</th>
                                    <th className='col-md-2 col-sm-2'>Rinkeju skaicius</th>

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