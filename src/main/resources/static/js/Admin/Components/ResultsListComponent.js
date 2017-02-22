var ResultsListComponent = React.createClass({
  render: function() {
    var succesCreateMessage = alerts.showSuccesFixed(this.props.succesCreateText);
    var deleteResultsMessage = alerts.showDangerFixed(this.props.deletedResultsText);
    var self = this;
    var single = this.props.single.map(function(district,index){
      return (<ResultsSingleListRowComponent
                key={index}
                onHandleApprove={self.props.onHandleApprove}
                onHandleDelete={self.props.onHandleDelete}

                info={district}/>);
    });
    var multi = this.props.multi.map(function(district,index){
      return (<ResultsMultiListRowComponent
                onHandleApprove={self.props.onHandleApprove}
                onHandleDelete={self.props.onHandleDelete}
                key={index}
                info={district}
              />);
    });

    return (
      <div className="panel panel-default">
      <div className="panel-heading" style={{paddingTop:20,paddingBottom:20}}>
        <h4 style={{display:'inline'}}><i className="fa fa-table"></i>&nbsp; Rezultatai</h4>
        <div className="text-success pull-right">
          <a id='result-refresh' onClick={this.props.onHandleRefresh} className="text-success"><i className="fa fa-refresh fa-lg"></i></a>
        </div>
      </div>
        <div className="panel-body">
          {succesCreateMessage}
          {deleteResultsMessage}
          <div className="tab-content">
            <ul className="nav nav-tabs">
              <li className="active"><a data-toggle="tab" href="#results0">Tvarka</a></li>
              <li><a data-toggle="tab" href="#results1"> Vienmandatė </a></li>
              <li><a data-toggle="tab" href="#results2"> Daugiamandatė </a></li>
            </ul>
              <div className="tab-content">
                <div id="results0" className="tab-pane fade in active">
                  <h3>Rezultatų patvirtinimo tvarka</h3>
                  <p>1. Suskaičiavę balsus, apylinkių atstovai įveda rezultatus į sistemą</p>
                  <p>2. Rezultatai įvedami tik vieną kartą</p>
                  <p>3. Rezultatus tvirtina arba ištrina sistemos administratorius</p>
                  {/*<p>4. Rezultatai negali būti tvirtinami 5 min. nuo įvedimo, siekiant išvengti klaidingų duomenų paskelbimo</p>*/}
                  <p>4. Ištrinus rezultatus, apylinkių atstovai gali juos įvesti iš naujo</p>
                  <p>5. Patvirtinti rezultatai tampa matomi visuomenei</p>
                </div>
                <div id="results1" className="tab-pane fade">
                  <h3>Vienmandatės rezultatai</h3>
                  <table width="100%" className="table table-striped table-hover" id="dataTables-example">
                    <thead>
                      <tr>
                        <th> Apylinkės pavadinimas </th>
                        <th> Apylinkės atstovas </th>
                        <th> Registravimo data/laikas </th>
                        <th> &nbsp; Veiksmai &nbsp; </th>
                      </tr>
                    </thead>
                    <tbody>
                      {single}
                    </tbody>
                  </table>
                </div>
                <div id="results2" className="tab-pane fade">
                  <h3>Daugiamandatės rezultatai</h3>
                  <table width="100%" className="table table-striped table-hover" id="dataTables-example">
                    <thead>
                      <tr>
                        <th> Apylinkės pavadinimas </th>
                        <th> Apylinkės atstovas </th>
                        <th> Registravimo data/laikas </th>
                        <th> &nbsp; Veiksmai &nbsp; </th>
                      </tr>
                    </thead>
                    <tbody>
                      {multi}
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

window.ResultsListComponent = ResultsListComponent;
