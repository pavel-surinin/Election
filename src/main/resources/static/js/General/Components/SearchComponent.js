var SearchComponent = React.createClass({

  render: function() {
    var counties = this.props.results[0].map(function(county,index){
      return <tr key={index}><td><a href={'#/county/' + county.id}>{county.name}</a></td></tr>;
    });
    var districts = this.props.results[1].map(function(district,index){
      return <tr key={index}><td><a href={'#/county/' + district.countyId + '/' + district.id}>{district.name}</a> <small>({district.countyName} apygarda)</small></td></tr>;
    });
    var reps = this.props.results[2].map(function(rep,index){
      return <tr key={index}><td><a href={'#/county/' + rep.countyId + '/' + rep.districtId}>{rep.name} {rep.surname}</a></td></tr>;
    });
    var cand = this.props.results[3].map(function(cand,index){
      return <tr key={index}><td><a href={'#/candidate/' + cand.id}>{cand.name} {cand.surname}</a></td></tr>;
    });
    var count = this.props.results[0].length +this.props.results[1].length +this.props.results[2].length + this.props.results[3].length;
    return (
      <div>
        <div className="container">
          <h1 className='yellow'>Paieška: {this.props.searchFor}</h1>
        </div>
        <div id="exTab1" className="container shadow">
          <div className="tab-content clearfix">
            <table width="100%" className="table" id="searchable-table">
              <thead>
                <tr>
                  <th>Paieškos rezultatai ({count})</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <th>Apygardos ({this.props.results[0].length})</th>
                </tr>
                {counties}
                <tr>
                  <th>Apylinkės  ({this.props.results[1].length})</th>
                </tr>
                {districts}
                <tr>
                  <th>Apylinių atstovai  ({this.props.results[2].length})</th>
                </tr>
                {reps}
                <tr>
                  <th>Kandidatai  ({this.props.results[3].length})</th>
                </tr>
                {cand}
                </tbody>
              </table>
            </div>
          </div>
          </div>
    );
  }

});

window.SearchComponent = SearchComponent;
