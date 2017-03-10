function getCandidate(self) {
  axios
    .get('/candidate/' + self.props.params.id)
    .then(function(response){
      self.setState({
        info :  response.data,
        isLoading : false,
      });
    })
    .catch(function(err){
      console.error('CandidateDetailContainer.componentWillMount.axios.get.candidate', err);
    });
}

var CandidateDetailContainer = React.createClass({
  getInitialState: function() {
    return {
      info : [],
      isLoading : true,
    };
  },
  componentWillMount: function() {
    getCandidate(this);
  },
  render: function() {
    document.title = 'Kandidatas: ' +  this.state.info.name + ' ' + this.state.info.surname + ' - Rinkimai 2017';
    if (this.state.isLoading) {
      if (this.state.isLoading) {
        return <div style={{marginTop : '3px', color : 'white', textAlign : 'center'}}><i className="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></i></div>;
      }
    }
    return (
      <CandidateDetailComponent info={this.state.info}/>
    );
  }
});

window.CandidateDetailContainer = CandidateDetailContainer;
