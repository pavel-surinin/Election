var CandidatesListContainer = React.createClass({

  getInitialState: function() {
    return {
      candidateList : [],
      isLoading : true,
      succesCreateText: '',
      fade : {backgroundColor : 'yellow'},
    };
  },

  componentWillMount: function() {
    var self = this;
    axios
      .get('/candidate')
      .then(function(response){
        self.setState({
          candidateList :  response.data,
          isLoading : false,
        });
      })
      .catch(function(err){
        console.error('CandidatesListContainer.componentWillMount.axios.get.candidate', err);
      });
  },
  componentDidMount: function() {
    this.setState({
      fade : {backgroundColor : 'green'},
    });
  },
  render: function() {
    document.title='Kandidatai Rinkimai 2017';
    if (this.state.isLoading) {
      return <div style={{marginTop : '3px', color : 'white', textAlign : 'center'}}><i className="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></i></div>;
    } else {
      return (
        <CandidatesList
          candidateList={this.state.candidateList}
          succesCreateText={this.state.succesCreateText}
          fade={this.state.fade}
        />
      );
    }

  }

});

window.CandidatesListContainer = CandidatesListContainer;
